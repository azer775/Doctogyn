# app/services/blood_extraction.py

import json
import os
from typing import Any, Dict, List
from fastapi import HTTPException, UploadFile
from langchain.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnablePassthrough
from traitlets import This

# from app.models.blood_test import BloodTestResult
from app.core.llm_client import LLMClient
from app.models.blood_test import BloodTestResult
from app.services.text_extraction import TextExtractionService


class BloodExtractionService:
    def __init__(self):
        self.llm = LLMClient.get_instance()
        self.text_extractor = TextExtractionService()

    def _build_chain(self, reference_data: List[Dict[str, Any]]):
        system_prompt = """You are a helpful assistant. The provided text is extracted from lab test result documents (pdf, images). 
Your task is to extract the lab results from the following text and format them into a JSON.

IMPORTANT: The final JSON must always be a **LIST of documents**. 
Each document corresponds to one uploaded lab report (marked by ***document1***, ***document2***, etc.).
All the results in a given document should share the same "Date" (the date of the document header). 
Different documents may have different dates.

---

### OUTPUT FORMAT
The OUTPUT JSON must always look like this:

[
  {{
    "CytologyPathology": [...],
    "Genetic": [...],
    "Biology": [...],
    "Serology": [...],
    "Bacteriology": [...],
    "Radiology": [...],
    "Spermogramme": [...],
    "Maternal Serum Marker": [...],
    "Hysterosalpingography": [...],
    "Blood Group": [...]
  }},
  {{
    ...
  }}
]

⚠️ Rules:
- Each DOCUMENT = one object in the list.
- Each DOCUMENT corresponds to one uploaded file or section (***documentX***).
- If a category has no results in that document, **omit the category entirely** (do not include empty arrays).
- If a category has multiple test results, include them all inside the array.
- Never add categories or tests that are not present in the SYSTEM-USER INPUT JSON.

---

### EXTRACTION STEPS
Step 1: Merge the ***SYSTEM INPUT JSON*** with the ***USER INPUT JSON*** into one unified list of possible tests 
(called SYSTEM-USER INPUT JSON). This defines the possible test names and structures. 

Step 2: For each ***documentX*** in the text:
- Identify the date of the document (ignore dates from older results mentioned inside the text).
- Identify lab results that match one of the SYSTEM-USER INPUT JSON tests.
- Map them to the correct category.
- If multiple values for the same test exist, create multiple objects.

Step 3: Produce the OUTPUT JSON as a list of documents, following the exact rules above.

---

### FIELD RULES
1. Numerical values: keep decimals as written (e.g. 11.23 → 11.23). 
   If a value is "<0.01", write `0.01`.

2. Blood Group / Rh mapping:
- A positive: 1
- A negative: 2
- B positive: 3
- B negative: 4
- AB positive: 5
- AB negative: 6
- O positive: 7
- O negative: 8

3. Bacteriology "Germ" mapping (field can contain multiple values, e.g. "2,3"):  
- No germ: 0
- Candida glabrata: 1
- Streptococcus agalactiae: 2
- Uréaplasma Uréalyticum: 3
- Staphylococcus saprophyticus: 4
- Candida Albicans: 5
- Escherichia Coli: 6
- Gardnerella vaginalis: 7
- Clue cells: 8
- Candida spp: 9
- Staphylococcus aureus: 10
- Mycoplasma hominis: 11
- Candida non albicans: 12
- Klebsiella pneumoniae: 13
- Trichomonas vaginalis: 15
- Strepto B: 16
- Enterococcus faecalis: 17
- Staphylocoque coagulase negative: 18
- Enterococcus spp: 19
- Enterobacter cloacae: 20

4. Genetic "Sample-origin" mapping:
- Maternal Blood: -1
- Paternal Blood: -2
- Amniotic fluid: -3

5. Hysterosalpingography mapping (each field can take multiple values, e.g. "-1,-2"):  
Uterus:
- lblUsualAppearance: -1
- lblIrregularOutline: -2
- lblBicornuateSeptate: -3
- lblUnicornuate: -4
- lblLargeSize: -5
- lblSmallSize: -6
- lblNotDemonstrated: -7
- lblSmoothFillingDefect: -8
- lblIrregularFillingDefect: -9

Tube:
- lblUsualAppearance: -1
- lblNotDemonstrated: -2
- lblFullyDemonstrated: -3
- lblCornualObstruction: -4
- lblIsthmicObstruction: -5
- lblDistalObstruction: -6
- lblHydrosalpinx: -7
- lblMotionless: -8
- lblAscended: -9

Cervix:
- lblUsualAppearance: -1
- lblIrregularOutline: -2
- lblBicornuateSeptate: -3
- lblLargeSize: -4
- lblSmallSize: -5
- lblNotDemonstrated: -6
- lblSmoothFillingDefect: -7
- lblIrregularFillingDefect: -8

6. Interpretation rules:
- Biology: Normal=1, Low=2, High=3
- Serology: Negative=1, Positive=2, Equivocal=3
- Bacteriology: Negative=1, Positive=2
- Radiology: Normal=1, Abnormal=2

7. Maternal Serum Marker:
- If PAPP-A present → first trimester: only include PAPP-A and free beta hCG.
- If no PAPP-A → second trimester: only include AFP, HCG (and optionally oestriol).
- Only include combined risk, not age or chemistry-based risks.

8. Comments: only include the original interpretation text from the input (e.g. "immunisation status"). 
   Can be empty if nothing is relevant.

---

### REFERENCE
Reference tests: {reference}

---
"""
        human_prompt = "Extract results from this report:\n\n{text}"
        prompt = ChatPromptTemplate.from_messages([
            ("system", system_prompt),
            ("human", human_prompt)
        ])
 
        return (
            RunnablePassthrough.assign(
                reference=lambda _: json.dumps(reference_data)
            )
            | prompt
            | self.llm
        )

    
        
    def load_reference_files(self,file_paths: List[str]) -> List[Dict[str, Any]]:
        combined_data = []
        for path in file_paths:
            print(f"Loading reference file: {path}")
            if not os.path.exists(path):
                print(f"Error: File not found - {path}")
                continue
                
            with open(path, "r", encoding="utf-8") as f:
                data = json.load(f)
                
                # Handle the nested category structure
                if isinstance(data, dict) and "Categories" in data:
                    for category_name, category_items in data["Categories"].items():
                        if isinstance(category_items, list):
                            # Add category information to each item
                            for item in category_items:
                                if isinstance(item, dict):
                                    item["category"] = category_name
                                    combined_data.append(item)
                else:
                    print(f"Unexpected structure in {path} - expected 'Categories' dictionary")
        
        print(f"Loaded {len(combined_data)} reference items")
        return combined_data
    async def extract(self, file: UploadFile) -> Dict:
        raw_text = await self.text_extractor.extract_text(file)
        reference_json = self.load_reference_files([
            "C:/Users/azerb/Downloads/biology.json"
        ])
        chain = self._build_chain(reference_json)
        response = await chain.ainvoke({"text": raw_text})
        print("Full LLM response:",response.content)
        try:
            return json.loads(response.content)
        except json.JSONDecodeError as e:
            raise HTTPException(400, f"Failed to parse LLM response: {str(e)}")
    