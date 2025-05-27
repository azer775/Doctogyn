
from typing import List
from fastapi import APIRouter, File, UploadFile
from traitlets import This

from app.services.blood_extraction import BloodExtractionService


router = APIRouter()
blood_service = BloodExtractionService()

@router.post("/extract-blood-data-with-embedding")
async def extract_blood_data_embedding(files: List[UploadFile] = File(...)):
    text=await blood_service.extract(files)
    return  text