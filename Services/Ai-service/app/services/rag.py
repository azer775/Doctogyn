import os
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langchain_community.vectorstores import Chroma
from langchain.chains import RetrievalQA
from langchain.prompts import PromptTemplate
from langchain_community.document_loaders import WebBaseLoader, PyPDFLoader
import requests
import unicodedata
import tempfile
from app.core.ChromaClient import ChromaClient
from app.services.ChunkingService import ChunkingService


class RagService:
    def __init__(self, collection_name: str = "rag_collection"):
        """Initialize the RAG pipeline with LLM and vector store."""
        self.collection_name = collection_name
        self.llm = ChatOpenAI(model_name="gpt-4", temperature=0.7)
        self.embeddings = OpenAIEmbeddings(model="text-embedding-ada-002")
        self.persist_dir = os.getenv("CHROMA_PERSIST_DIR", "./chroma_data")
        self.chunking_service = ChunkingService()
        
        # Initialize vector store
        chroma_client = ChromaClient.get_instance().get_client()
        self.vector_store = Chroma(
            collection_name=collection_name,
            embedding_function=self.embeddings,
            persist_directory=self.persist_dir,
            client=chroma_client
        )
        
        # Set up RAG prompt
        prompt_template = """You are a medical assistant expert.

                            Based on the following context and the provided summary of a patient's medical record, generate a concise list of **clinical alerts or recommended actions** the doctor should consider.

                            - Answer in the summary's language.
                            - Focus on actionable insights that can be derived from the medical record.
                            - Present your recommendations as clear, actionable bullet points.
                            - Be specific and medically sound.

                            Context:
                            {context}

                            Summary of the medical record:
                            {question}

                            Recommended actions:"""
        
        self.prompt = PromptTemplate(
            template=prompt_template, input_variables=["context", "question"]
        )
        
        # Initialize RAG pipeline
        self.qa_chain = RetrievalQA.from_chain_type(
            llm=self.llm,
            chain_type="stuff",
            retriever=self.vector_store.as_retriever(search_kwargs={"k": 5}),
            chain_type_kwargs={"prompt": self.prompt},
            return_source_documents=True
        )

    def load_and_store_documents(self, urls: list[str]) -> int:
        """Load documents from URLs, chunk them, and store in ChromaDB."""
        print("Loading and storing documents...")
        try:
            documents = []
            for url in urls:
                try:
                    if url.lower().endswith('.pdf'):
                        # Handle PDF URLs
                        response = requests.get(url, stream=True)
                        response.raise_for_status()
                        with tempfile.NamedTemporaryFile(delete=False, suffix='.pdf') as tmp_file:
                            tmp_file.write(response.content)
                            tmp_file_path = tmp_file.name
                        loader = PyPDFLoader(tmp_file_path)
                        pdf_docs = loader.load()
                        for doc in pdf_docs:
                            # Clean Unicode
                            doc.page_content = unicodedata.normalize('NFKD', doc.page_content)
                            doc.page_content = ''.join(c if ord(c) < 128 else ' ' for c in doc.page_content)
                            doc.page_content = doc.page_content.replace("\\", "\\\\")
                            # Set metadata
                            doc.metadata = {"url": url}
                            documents.append(doc)
                        os.unlink(tmp_file_path)
                    else:
                        # Handle HTML URLs
                        loader = WebBaseLoader(url)
                        web_docs = loader.load()
                        for doc in web_docs:
                            # Clean Unicode
                            doc.page_content = unicodedata.normalize('NFKD', doc.page_content)
                            doc.page_content = ''.join(c if ord(c) < 128 else ' ' for c in doc.page_content)
                            doc.page_content = doc.page_content.replace("\\", "\\\\")
                            # Set metadata
                            doc.metadata = {"url": url}
                            documents.append(doc)
                except Exception as e:
                    print(f"Error loading document from {url}: {e}")
            
            if not documents:
                return 0
            
            # Use ChunkingService to chunk and store
            self.chunking_service.add_documents_to_chroma(documents, self.collection_name)
            return len(documents)
        except Exception as e:
            raise ValueError(f"Failed to load and store documents: {e}")

    def query_rag(self, question: str) -> dict:
        """Query the RAG pipeline and return answer with source documents."""
        try:
            result = self.qa_chain.invoke({"query": question})
            sources = [
                {"content": doc.page_content[:500], "metadata": doc.metadata}
                for doc in result.get("source_documents", [])
            ]
            return {"answer": result["result"], "sources": sources}
        except Exception as e:
            raise ValueError(f"Error querying RAG pipeline: {e}")