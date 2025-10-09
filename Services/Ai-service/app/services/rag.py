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
from app.models.alert_request import AlertRequest
from bs4 import BeautifulSoup

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
        prompt_template = """You are an AI assistant specialized in medical analysis. You will receive:

1. A resume of a medical record for a patient, summarizing the current consultation (including patient details, symptoms, diagnoses, prescriptions, tests ordered, and doctor's notes/actions).
2. The last consultation details.
3. Relevant scientific data (e.g., excerpts from guidelines, studies, or evidence-based recommendations from sources like WHO, ACOG, or peer-reviewed papers). Use this data only if it is directly relevant and evidence-based to identify omissions or mistakes—do not invent or assume information beyond what's provided.

Your task: Analyze the medical record resume in the context of the current consultation only. Generate alerts that highlight any potential omissions or mistakes in the doctor's approach, such as missing prescriptions, screenings, monitorings, or follow-ups based on standard medical practices inferred from the provided scientific data (if applicable). Alerts must be concise, factual, and tied directly to the consultation details. If no issues are found, return an empty list.

Focus solely on the current consultation—ignore past history unless it directly impacts the present.
Only use scientific data if it efficiently supports identifying a gap (e.g., cite briefly in the alert if it strengthens the point, like "per WHO guidelines").
Do not add alerts for non-omissions (e.g., no positive feedback, only potential issues).

Examples of alerts (for illustration; generate based on input):
- "No iron supplementation prescribed for a pregnant woman"
- "No folic acid prescribed in early pregnancy"
- "Missing screening or monitoring for diabetes or hypertension"

Relevant scientific data (Context):
{context}

Resume of the medical record:
{resume}

Last consultation:
{consultation}

Output strictly in JSON format conforming to this schema (no additional text, explanations, or fields):
{{
    "alerts": [
        "string describing the alert 1",
        "string describing the alert 2"
    ]
}}

Generate alerts:"""
        
        self.prompt = PromptTemplate(
            template=prompt_template, input_variables=["context", "resume", "consultation"]
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
                            print(doc.page_content)
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

    def query_rag(self, alert_request: AlertRequest) -> dict:
        """Query the RAG pipeline and return answer with source documents."""
        try:
            # Retrieve relevant documents based on the resume and consultation
            query_text = f"{alert_request.resume}\n\n{alert_request.consultation}"
            relevant_docs = self.vector_store.as_retriever(search_kwargs={"k": 5}).get_relevant_documents(query_text)
            
            # Build context from retrieved documents
            context = "\n\n".join([doc.page_content for doc in relevant_docs])
            
            # Format the prompt with all required variables
            formatted_prompt = self.prompt.format(
                context=context,
                resume=alert_request.resume,
                consultation=alert_request.consultation
            )
            
            # Invoke the LLM
            result = self.llm.invoke(formatted_prompt)
            
            # Extract source metadata
            sources = [
                {"content": doc.page_content[:500], "metadata": doc.metadata}
                for doc in relevant_docs
            ]
            
            return {"answer": result.content, "sources": sources}
        except Exception as e:
            raise ValueError(f"Error querying RAG pipeline: {e}")