
from typing import List
from fastapi import APIRouter, File, UploadFile
from traitlets import This

from app.models.summaryClasses import FinalResponse, SummaryRequest
from app.services.analysis_extraction import BloodExtractionService
from app.services.summary import SummaryService
from app.services.rag import RagService

router = APIRouter()
blood_service = BloodExtractionService()
summary_service = SummaryService()
rag_service = RagService()
@router.post("/extract-blood-data-with-embedding")
async def extract_blood_data_embedding(files: List[UploadFile] = File(...)):
    text=await blood_service.extract(files)
    return  text 
@router.post("/to-markdown")
async def to_markdown(html_content: SummaryRequest) -> FinalResponse:
    summary = summary_service.analyze_medical_record(html_content.text, html_content.abbreviations)
    return summary
@router.post("/testvector")
async def test_vector():
    result = rag_service.query_rag("Echographies gynécologiques et obstétriques régulières- Echographie mammaire normale- Sérologie rubéole: positive- Sérologie toxoplasmose: négative- Glycémie à jeun: normale- HGPO 75: normale- Prolactinémie, B HCG qualitative, et marqueurs sériques maternels effectués- ECBU: négative- Prélèvement vaginal: positif pour Candida non albicans- Sérologie toxoplasmose IgG: négative")
    return {"message": "Test vector endpoint", "result": result} 