import json
from typing import List, Optional
from html_to_markdown import convert_to_markdown
from langchain.prompts import PromptTemplate

from app.core.summary_client import SummaryClient
from app.models.summaryClasses import AbbreviationDefinition, FinalResponse


class SummaryService:
    def __init__(self):
        self.llm = SummaryClient.get_instance()
        pass

    def generate_summary(self, html_content: str) -> str:
        markdown_content = convert_to_markdown(html_content)
        # Here you would implement your summarization logic
        return markdown_content
    prompt_template = PromptTemplate(
        template="""
    Analyze this medical record of a gynecology patient and return JSON in EXACTLY this format:
    {{
        "responsetype": "summary" OR "abbreviation_issue",
        "summary": "concise_summary_text" OR "",
        "unrecognized_abbreviations": [
            {{
                "Abbreviation": "abbr1",
                "PossibleMeanings": ["meaning1", "meaning2"]
            }},
            {{
                "Abbreviation": "abbr2",
                "PossibleMeanings": ["meaning3", "meaning4"]
            }}
        ] OR null
    }}
    STEPS TO FOLLOW:
    1. Read the medical record text and identify the language used.
    2. Identify any abbreviations in the text with taking into account the language.
    3. If there are any unrecognized abbreviations, use the provided abbreviations to replace them in the text.

    STRICT RULES:
    1. For abbreviations listed above (both standard and personalized):
    - USE their provided meanings
    - DO NOT list them as unrecognized
    2. Only flag abbreviations NOT found above as unrecognized
    3. For unrecognized abbreviations:
    - Provide 2-3 possible medical meanings
    - Meanings should be clinically relevant
    4. If you understand the text completely:
    - Set "responsetype" to "summary"
    - Provide a concise summary in "summary"
    - Set "unrecognized_abbreviations" to null
    5. If you find unrecognized abbreviations:
    - Set "responsetype" to "abbreviation_issue"
    - List all unrecognized abbreviations with possible meanings
    - Set "summary" to an empty string ""
    IMPORTANT:
    - NEVER list standard or personalized abbreviations as unrecognized
    - ALWAYS include all three fields
    - "summary" and "unrecognized_abbreviations" are mutually exclusive
    - Don't include any other fields
    - Maintain exact JSON structure shown above 
    Medical Record:
    {record_text}


    """,
        input_variables=["record_text"]
    )
    def analyze_medical_record(self, record_text: str, user_abbreviations: Optional[List[AbbreviationDefinition]] = None) -> FinalResponse:
        # Build the personalized abbreviations context
        record_text = self.generate_summary(record_text)
        abbreviation_context = ""
        if user_abbreviations:
            abbreviation_context = "\n\nPERSONALIZED ABBREVIATIONS:\n" + \
                "\n".join(f"- {abbr.abbreviation}: {abbr.meaning}" for abbr in user_abbreviations)
        
        # Add standard medical abbreviations context
        standard_abbreviations = """
    STANDARD MEDICAL ABBREVIATIONS:
    - BP: Blood Pressure
    - HR: Heart Rate
    - RR: Respiratory Rate
    - BMI: Body Mass Index
    - CBC: Complete Blood Count
    - ECG/EKG: Electrocardiogram
    - CT: Computed Tomography
    - MRI: Magnetic Resonance Imaging
    - ICU: Intensive Care Unit
    - ER: Emergency Room
    """
        
        # Combine everything in the prompt
        full_context = f"{standard_abbreviations}{abbreviation_context}\n\nMEDICAL RECORD TO ANALYZE:\n{record_text}"
        
        _prompt = self.prompt_template.format(record_text=full_context)
        #print("Prompt sent to LLM:", _prompt)  # Debugging line to see the prompt
        response = self.llm.invoke(_prompt)
        
        try:
            # Parse directly into FinalResponse
            return FinalResponse.model_validate_json(response.content)
        except Exception as e:
            # Fallback parsing
            try:
                raw_data = json.loads(response.content)
                # Ensure required fields exist
                if "summary" not in raw_data:
                    raw_data["summary"] = ""
                if "unrecognized_abbreviations" not in raw_data:
                    raw_data["unrecognized_abbreviations"] = None
                return FinalResponse.model_validate(raw_data)
            except Exception as e:
                raise ValueError(f"Failed to parse LLM response: {str(e)}. Raw response: {response.content}")