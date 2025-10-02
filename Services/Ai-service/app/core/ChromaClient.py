from dotenv import load_dotenv
import os
import chromadb

load_dotenv()

class ChromaClient:
    _instance = None

    @staticmethod
    def get_instance():
        """Return the singleton ChromaClient instance."""
        if ChromaClient._instance is None:
            ChromaClient()
        return ChromaClient._instance

    def __init__(self):
        """Initialize the ChromaDB HTTP client."""
        if ChromaClient._instance is not None:
            raise Exception("**********************ChromaClient is a singleton! RUNNING*******************")
        
        # Load connection details from environment variables
        host = os.getenv("CHROMA_HOST", "localhost")
        port = int(os.getenv("CHROMA_PORT", 8010))
        
        try:
            # Initialize HTTP client to connect to ChromaDB server
            self.client = chromadb.HttpClient(host=host, port=port)
            # Test connection with heartbeat
            heartbeat = self.client.heartbeat()
            print(f"Connected to ChromaDB at {host}:{port}, Heartbeat: {heartbeat}")
            ChromaClient._instance = self
        except Exception as e:
            raise Exception(f"Failed to initialize ChromaClient: {e}")

    def get_client(self):
        """Return the ChromaDB client instance."""
        return self.client