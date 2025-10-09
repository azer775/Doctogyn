import os
from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma
from langchain.text_splitter import RecursiveCharacterTextSplitter
from app.core.ChromaClient import ChromaClient
from langchain.docstore.document import Document

class ChunkingService:
    def __init__(self, chunk_size: int = 1000, chunk_overlap: int = 200):
        """Initialize the text splitter and embeddings for chunking and storage."""
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
    
    def get_urls_from_collection(self, collection_name: str = "rag_collection") -> list[str]:
        """
        Retrieve unique URLs from the metadata of all documents in the specified collection.
        
        Args:
            collection_name: Name of the ChromaDB collection (default: "rag_collection")
            
        Returns:
            A list of unique URLs found in the document metadata
        """
        try:
            chroma_client = ChromaClient.get_instance().get_client()
            vector_store = Chroma(
                collection_name=collection_name,
                embedding_function=self.embeddings,
                persist_directory=self.persist_dir,
                client=chroma_client
            )
            
            # Get all documents from the collection
            collection = chroma_client.get_collection(name=collection_name)
            results = collection.get()
            
            # Extract unique URLs from metadata
            urls = set()
            if results and 'metadatas' in results:
                for metadata in results['metadatas']:
                    if metadata and 'url' in metadata:
                        urls.add(metadata['url'])
            
            return sorted(list(urls))
        except Exception as e:
            raise ValueError(f"Failed to retrieve URLs from collection: {e}")
    