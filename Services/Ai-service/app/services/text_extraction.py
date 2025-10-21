

from io import BytesIO
from typing import List
import spacy
from fastapi import UploadFile
import fitz
import pdfplumber
import pytesseract
from traitlets import This
from PIL import Image
import os

# Set Tesseract executable and tessdata path
pytesseract.pytesseract.tesseract_cmd = r"C:/Program Files/Tesseract-OCR/tesseract.exe"
os.environ['TESSDATA_PREFIX'] = r"C:/Program Files/Tesseract-OCR/tessdata"


class TextExtractionService:
     
    def __init__(self):
        
        pass

    async def extract_text1(self,file: UploadFile) -> tuple[str, bytes]:
        """Try to extract structured text (non-OCR) from PDF."""
        try:
            contents = await file.read()
            extension = file.filename.lower().split('.')[-1]

            if extension != "pdf":
                return "", contents  # Skip structured extraction for images

            pdf_file = BytesIO(contents)
            text_parts = []

            with pdfplumber.open(pdf_file) as pdf:
                for page in pdf.pages:
                    page_text = page.extract_text()
                    if page_text:
                        text_parts.append(page_text)

            full_text = "\n".join(text_parts).strip()
            full_text = redact_with_spacy(full_text)
            return full_text, contents

        except Exception as e:
            return "", contents  # Fallback to OCR on error
    async def extract_text(self,files: List[UploadFile]) -> str:
        """Extract text from multiple files; try structured first, fallback to OCR."""
        all_text = ""
        i=0 
        for file in files:
            print(f"Processing file: {file.filename}")
            text, content = await self.extract_text1(file)
            i+=1
            if not text.strip():
                print(f"Falling back to OCR for file: {file.filename}")
                ocr_text = self.perform_ocr_on_pdf(file, content, file.filename)
                all_text += f"\nDOCUMENT{i}\n{ocr_text}\nEND\n"
            else: 
                all_text += f"\nDOCUMENT{i}\n{text}\nEND\n"

        print("all_text",all_text)
        return "\n".join(all_text) + "\nDOCUMENT END\n"

    def perform_ocr_on_pdf(self, file: UploadFile, content: bytes, filename: str = None, dpi: int = 300) -> str:
        """OCR logic: for PDFs convert pages to images; for images, OCR directly."""
        ext = filename.lower().split('.')[-1]
        ocr_text_parts = [] 
        print("ext: ",ext)
        try: 
            # Try French first, fallback to English if not available
            try:
                lang = "fra"
                # Test if French is available
                pytesseract.get_languages()
            except:
                lang = "eng"
                print(f"Warning: French language not available, using English for {filename}")
            
            if ext == "pdf":
                doc = fitz.open(stream=content, filetype="pdf")
                for page in doc:
                    pix = page.get_pixmap(dpi=dpi)
                    img = Image.frombytes("RGB", [pix.width, pix.height], pix.samples)
                    text = pytesseract.image_to_string(img, lang=lang)
                    ocr_text_parts.append(text)
            elif ext in ["png", "jpg", "jpeg", "bmp", "tiff"]:
                img = Image.open(BytesIO(content))
                text = pytesseract.image_to_string(img, lang=lang) 
                print("ocr_text_parts",text)
                ocr_text_parts.append(text)
                print("ocr_text_parts",text)    
            else:
                return f"[ERROR: Unsupported file type for OCR: {filename}]"
        except Exception as e:
            return f"[ERROR: OCR failed for {filename}: {e}]"
        return "\n".join(redact_with_spacy(text) for text in ocr_text_parts).strip()
nlp = spacy.load("en_core_web_md")     
def redact_with_spacy(text: str) -> str:
    doc = nlp(text)
    redacted = text
    for ent in doc.ents:
        if ent.label_ in ["PERSON", "ORG", "LOC"]:
            redacted = redacted.replace(ent.text, f" ")
    return redacted