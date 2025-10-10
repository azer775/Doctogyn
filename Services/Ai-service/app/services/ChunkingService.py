import os
from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma
from langchain.text_splitter import RecursiveCharacterTextSplitter
from app.core.ChromaClient import ChromaClient
from langchain.docstore.document import Document

class ChunkingService:
    def __init__(self, chunk_size: int = 1500, chunk_overlap: int = 300):
        """Initialize the text splitter and embeddings for chunking and storage.
        
        Args:
            chunk_size: Maximum size of each chunk (default: 1500 for medical context)
            chunk_overlap: Overlap between chunks to maintain context (default: 300)
        """
        # Use RecursiveCharacterTextSplitter with medical-optimized separators
        # Separators ordered by priority - try to split at natural boundaries first
        self.text_splitter = RecursiveCharacterTextSplitter(
            chunk_size=chunk_size,
            chunk_overlap=chunk_overlap,
            length_function=len,
            separators=[
                "\n\n\n",      # Multiple line breaks (section boundaries)
                "\n\n",        # Paragraph breaks
                "\n",          # Line breaks
                ". ",          # Sentence endings
                "! ",          # Exclamation endings
                "? ",          # Question endings
                "; ",          # Semicolon (often used in medical lists)
                ": ",          # Colon (medical terms, definitions)
                ", ",          # Comma (last resort for lists)
                " ",           # Word boundaries
                ""             # Character-level (absolute fallback)
            ],
            # Keep related content together
            keep_separator=True,  # Preserve separators for context
            is_separator_regex=False
        )
        self.embeddings = OpenAIEmbeddings(model="text-embedding-ada-002", openai_api_key=os.getenv("OPENAI_API_KEY"))
        self.persist_dir = os.getenv("CHROMA_PERSIST_DIR", "./chroma_data")

    def chunk_text(self, text: str) -> list[str]:
        """Split text into chunks using RecursiveCharacterTextSplitter with preprocessing.
        
        Args:
            text: The text to split into chunks
            
        Returns:
            List of text chunks optimized for medical content
        """
        try:
            # Preprocess text for better chunking
            # Normalize whitespace while preserving paragraph structure
            lines = text.split('\n')
            processed_lines = []
            
            for line in lines:
                # Remove excessive spaces but keep structure
                cleaned = ' '.join(line.split())
                if cleaned:  # Only add non-empty lines
                    processed_lines.append(cleaned)
            
            processed_text = '\n'.join(processed_lines)
            
            # Split text into chunks
            chunks = self.text_splitter.split_text(processed_text)
            
            if not chunks:  # Fallback if splitting fails
                chunks = [processed_text if processed_text else text]
            
            print(f"Created {len(chunks)} chunks from text (avg size: {sum(len(c) for c in chunks)//len(chunks) if chunks else 0} chars)")
            return chunks
        except Exception as e:
            print(f"Text splitting failed: {e}. Falling back to single chunk.")
            return [text]

    def add_documents_to_chroma(self, documents: list[Document], collection_name: str = "rag_collection") -> Chroma:
        """Add documents or their chunks to ChromaDB with OpenAI embeddings.
        
        Args:
            documents: List of Document objects to chunk and store
            collection_name: Name of the ChromaDB collection
            
        Returns:
            Chroma vector store instance
        """
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
            for doc_idx, doc in enumerate(documents):
                chunks = self.chunk_text(doc.page_content)
                
                # Create document for each chunk with enhanced metadata
                for chunk_idx, chunk in enumerate(chunks):
                    # Copy original metadata and add chunk information
                    chunk_metadata = doc.metadata.copy() if doc.metadata else {}
                    chunk_metadata.update({
                        "chunk_index": chunk_idx,
                        "total_chunks": len(chunks),
                        "doc_index": doc_idx,
                        "chunk_size": len(chunk)
                    })
                    
                    chunked_docs.append(
                        Document(page_content=chunk, metadata=chunk_metadata)
                    )
            
            # Add chunked documents to Chroma
            if chunked_docs:
                print(f"Adding {len(chunked_docs)} chunks to ChromaDB collection '{collection_name}'")
                vector_store.add_documents(chunked_docs)
            else:
                print("No chunks to add to ChromaDB")
            
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
    