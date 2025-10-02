import os
from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma
from langchain.text_splitter import RecursiveCharacterTextSplitter
from app.core.ChromaClient import ChromaClient
from langchain.docstore.document import Document

class ChunkingService:
    def __init__(self, chunk_size: int = 1000, chunk_overlap: int = 200):
        """Initialize the text splitter and embeddings for chunking and storage."""
        print("***********************Initializing ChunkingService*******************")
        # Use RecursiveCharacterTextSplitter for efficient, deterministic chunking
        self.text_splitter = RecursiveCharacterTextSplitter(
            chunk_size=chunk_size,
            chunk_overlap=chunk_overlap,
            length_function=len,
            separators=["\n\n", "\n", " ", ""]
        )
        self.embeddings = OpenAIEmbeddings(model="text-embedding-ada-002", openai_api_key=os.getenv("OPENAI_API_KEY"))
        self.persist_dir = os.getenv("CHROMA_PERSIST_DIR", "./chroma_data")

    def chunk_text(self, text: str) -> list[str]:
        """Split text into chunks using RecursiveCharacterTextSplitter."""
        print("***********************Chunking text*******************")
        try:
            chunks = self.text_splitter.split_text(text)
            if not chunks:  # Fallback if splitting fails
                chunks = [text]
            print(f"Created {len(chunks)} chunks from text")
            return chunks
        except Exception as e:
            print(f"Text splitting failed: {e}. Falling back to single chunk.")
            return [text]

    def add_documents_to_chroma(self, documents: list[Document], collection_name: str = "rag_collection") -> Chroma:
        """Add documents or their chunks to ChromaDB with OpenAI embeddings."""
        print("***********************Adding documents to ChromaDB*******************")
        try:
            chroma_client = ChromaClient.get_instance().get_client()
            vector_store = Chroma(
                collection_name=collection_name,
                embedding_function=self.embeddings,
                persist_directory=self.persist_dir,
                client=chroma_client
            )

            # Process each document: chunk and preserve metadata
            chunked_docs = []
            for doc in documents:
                chunks = self.chunk_text(doc.page_content)
                chunked_docs.extend([Document(page_content=chunk, metadata=doc.metadata) for chunk in chunks])
            
            # Add chunked documents to Chroma
            if chunked_docs:
                vector_store.add_documents(chunked_docs)
            
            return vector_store
        except Exception as e:
            raise ValueError(f"Failed to add documents to Chroma: {e}") 