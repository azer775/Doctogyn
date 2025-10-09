
from typing import List
from fastapi import APIRouter, File, UploadFile
from traitlets import This

from app.models.summaryClasses import FinalResponse, SummaryRequest
from app.models.alert_request import AlertRequest
from app.services.analysis_extraction import BloodExtractionService
from app.services.summary import SummaryService
from app.services.rag import RagService
from app.services.ChunkingService import ChunkingService

router = APIRouter()
blood_service = BloodExtractionService()
summary_service = SummaryService()
rag_service = RagService()
chunking_service = ChunkingService()
@router.post("/extract-blood-data-with-embedding")
async def extract_blood_data_embedding(files: List[UploadFile] = File(...)):
    text=await blood_service.extract(files)
    return  text 
@router.post("/to-markdown")
async def to_markdown(html_content: SummaryRequest) -> FinalResponse:
    summary = summary_service.analyze_medical_record(html_content.text, html_content.abbreviations)
    return summary
@router.post("/testvector")
async def test_vector(alert_request: AlertRequest):
    result = rag_service.query_rag(alert_request)
    return {"message": "Test vector endpoint", "result": result} 
@router.post("/scrape-and-query")
async def scrape_and_query():
    result = rag_service.load_and_store_documents(['https://pmc.ncbi.nlm.nih.gov/articles/PMC7170743/'])
    return {"message": "Scrape and query endpoint", "result": result} 
@router.post("/clean")
async def clean(html_content: str) -> FinalResponse:
    cleaned_content = rag_service.clean_html(html_content)
    return {"cleaned_content": cleaned_content} 

@router.get("/get-collection-urls")
async def get_collection_urls(collection_name: str = "rag_collection"):
    """
    Retrieve all unique URLs from the metadata of documents in the specified collection.
    
    Args:
        collection_name: Name of the ChromaDB collection (default: "rag_collection")
        
    Returns:
        A dictionary containing the collection name and list of unique URLs
    """
    try:
        urls = chunking_service.get_urls_from_collection(collection_name)
        return {
            "collection_name": collection_name,
            "url_count": len(urls),
            "urls": urls
        }
    except Exception as e:
        return {"error": str(e), "collection_name": collection_name}
