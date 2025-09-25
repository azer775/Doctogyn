
from typing import List
from fastapi import APIRouter, File, UploadFile
from traitlets import This

from app.models.summaryClasses import FinalResponse, SummaryRequest
from app.services.analysis_extraction import BloodExtractionService
from app.services.summary import SummaryService


router = APIRouter()
blood_service = BloodExtractionService()
summary_service = SummaryService()

@router.post("/extract-blood-data-with-embedding")
async def extract_blood_data_embedding(files: List[UploadFile] = File(...)):
    text=await blood_service.extract(files)
    return  text 
@router.post("/to-markdown")
async def to_markdown(html_content: SummaryRequest) -> FinalResponse:
    summary = summary_service.analyze_medical_record(html_content.text, html_content.abbreviations)
    print(summary)
    return summary