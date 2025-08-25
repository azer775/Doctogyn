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
        system_prompt = """You are a helpful assistant. The provided text is extracted from lab test result documents (pdf,images). Extract the lab results from the following text and format them into a JSON.
Each object in the json belongs to a category (biology, serolgy..) and corresponds to a lab test result. 
The output json should be formatted as in the input json. The input json is scattered between a ***SYSTEM INPUT JSON*** (in the system prompt)and ***USER INPUT JSON*** (in the user prompt).
The output json should have this structure and only these top level keys:
{{
document1{{
"CytologyPathology": []
"Genetic": []
"Biology": []
"Serology": []
"Bacteriology": []
"Radiology": []
"Spermogramme": []
"Maternal Serum Marker": []
"Hysterosalpingography": []
"Blood Group": []}}
document2{{...}}
}}

BE AS ACCURATE AS POSSIBLE. IT WOULD BE BETTER NOT TO EXTRACT A RIGHT DATA THAN TO EXTRACT WRONG ONE.
Follow these steps:
Step1: Read the ***SYSTEM INPUT JSON*** and complete it from the the ***USER INPUT JSON*** (if contains values) to understand the lab results test that could exist in the text. From this step you will consider a unique input json called SYSTEM-USER INPUT JSON.
Step2: Identify the lab results test that exist in the text and match one of the test in the SYSTEM-USER INPUT JSON (Remember that the input text could be in another language than English, usually). Identify the date of the tests. 
Step3: Generate the OUTPUT JSON where each object is a lab result test that belongs to a category. For each object, follow the sample object (the first one in each category). If there are multiple values for the same lab result type, replicate the object. Do not create objects or catagories that do not exist in the input json.
Ensure that:
1. For the numerical values fields, retain the exact decimal values from the text (e.g., if the text says 11.23, write 11.23) and avoid using letters or specific characters (for example, if the value in the text is "<0.01", write "0.01").
2. If no values are found for a specific object, delete the entire block from the JSON.
3. For Blood group and Rh, use the following rules:
- A positive: 1
- A negative: 2
- B positive: 3
- B negative: 4
- AB positive: 5
- AB negative: 6
- O positive: 7
- O negative: 8
4.For "Germ" field in the "Bacteriology" category, use the following rules ("Germ"field can take many values: for example, if the text says "Streptococcus agalactiae" and "Uréaplasma Uréalyticum", write 2,3):
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
5.for "Sample-origin" field in the "Genetic" category, use the following rules:
- Maternel Blood: -1
- Paternel Blood: -2
- Amniotic fluid: -3
6. In "hysterosalpingography" category fields, use the following rules (each field can take many values: for example, if the text says "lblUsualAppearance" and "lblIrregularOutline", write -1,-2):
Hysterosalpingography-uterus:
- lblUsualAppearance: -1  
- lblIrregularOutline: -2  
- lblBicornuateSeptate: -3  
- lblUnicornuate: -4  
- lblLargeSize: -5  
- lblSmallSize: -6  
- lblNotDemonstrated: -7  
- lblSmoothFillingDefect: -8  
- lblIrregularFillingDefect: -9  
Hysterosalpingography-tube:
- lblUsualAppearance: -1  
- lblNotDemonstrated: -2  
- lblFullyDemonstrated: -3  
- lblCornualObstruction: -4  
- lblIsthmicObstruction: -5  
- lblDistalObstruction: -6  
- lblHydrosalpinx: -7  
- lblMotionless: -8  
- lblAscended: -9  

Hysterosalpingography-cervix:
- lblUsualAppearance: -1
- lblIrregularOutline: -2
- lblBicornuateSeptate: -3
- lblLargeSize: -4
- lblSmallSize: -5
- lblNotDemonstrated: -6
- lblSmoothFillingDefect: -7
- lblIrregularFillingDefect: -8

7. For the "Interpretation" field, use the following rules: use your best knowledge to define the interpretation.
   - Biology:
     - Normal: 1
     - Low: 2
     - High: 3
     - Undefined: Omit the field
   - Serology:
     - Negative: 1
     - Positive: 2
     - Equivoqual: 3
     - Undefined: Omit the field
   - Bacteriology:
     - Negative: 1
     - Positive: 2
     - Undefined: Omit the field
   - Radiology:
     - Normal: 1
     - abnormal: 2
     - Undefined: Omit the field
8. For the "Date" field, use the date of the document (usually found in the first lines of the text).Sometimes, there is previous lab test results related to a diffrent dates, ignore them.
9. Don't change any key value in the provided json, only fill the values
IMPORTANT: Remember that we can upload many document (marked by ***document1***), process each one as an independant document. Each document contains a group of tests results. All the results in a given document should have the same date. But, in case of multiple documents, we could have different dates for each group of results.
10. For "Maternal Serum Marker" category, use the following rules: Start by defining First (if PAPP-A is present) or second trimester (if PAPP-A is not present). If first trimester, only PAPP-A and free beta hCG are required. If second trimester, only AFP, HCG are required (sometime oestriol is present). Only consider combined risk (not chemical or age-related risks)
11. For the "comment" fields, only put the original interpretation present in the input text (immunisation statut...). Do not include previous lab results. This field could be empty if no improtant information.
    Reference tests: {reference}"""
        human_prompt = "Extract results from this report:\n\n{text}"
        prompt = ChatPromptTemplate.from_messages([
            ("system", system_prompt),
            ("human", human_prompt)
        ])

        return (
            RunnablePassthrough.assign(
                schema=lambda _: BloodTestResult.model_json_schema(),
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

        try:
            return json.loads(response.content)
        except json.JSONDecodeError as e:
            raise HTTPException(400, f"Failed to parse LLM response: {str(e)}")
    