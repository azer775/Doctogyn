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
    You are a medical assistant that summarizes gynecology, obstetrics, and fertility medical records in French and verifies the quality of the doctor's approach.
The user input will contain three parts:

Known abbreviations (list of abbreviations and their meanings),
Medical record prior to the current consultation,
Current consultation.

The "medical record prior to the current consultation" will be organized as follows:

History: medical, surgical, etc.
Sub-records: can include gynecology, fertility, or obstetrics sub-records. You can identify the beginning of a sub-record by this format:
Example: Obstétrique (05/2024) indicates the obstetric subfolder created in May 2024.
Each sub-record contains consultations. You can identify the beginning of a consultation by this format:
Example: Consultation gynécologie (02/05/2023) indicates the gynecology consultation on May 2nd, 2023.
Each consultation may include the following information: reason for consultation, clinical examination, investigations, prescriptions, etc.
Analyze this medical record of a gynecology patient and return JSON in EXACTLY this format:
    {{
        "responsetype": "SUMMARY" OR "ABBREVIATION_ISSUE", 
        "summary": "concise_summary_text" OR "",
        "unrecognizedAbbreviation": [
            {{
                "abbreviation": "abbr1",
                "possibleMeanings": ["meaning1", "meaning2"]
            }},
            {{
                "abbreviation": "abbr2",
                "possibleMeanings": ["meaning3", "meaning4"]
            }}
        ] OR null
    }}
    STEPS TO FOLLOW:
    1. Read the medical record text and identify the language used.
    2. Identify any abbreviations in the text with taking into account the language.
    3. If there are any unrecognized abbreviations, use the provided abbreviations to replace them in the text.
    4. If only standard abbreviations are found, summarize the record.
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
    - If only standard abbreviations are found, summarize the record.
    Medical Record:
    {record_text}


    """,
        input_variables=["record_text"]
    )
    
    # Summary-only prompt template that focuses on generating summaries without checking for abbreviations
    summary_prompt_template = PromptTemplate(
        template="""
    Analyze this medical record of a gynecology patient and generate a comprehensive summary using the provided abbreviations.
    Return JSON in EXACTLY this format:
    {{
        "responsetype": "summary",
        "summary": "comprehensive_summary_text",
        "unrecognizedAbbreviation": null
    }}
    
    INSTRUCTIONS:
    1. Read the medical record text and identify the language used.
    2. Use ALL provided abbreviations (both standard and personalized) to understand the medical record fully.
    3. Generate a clear, comprehensive summary of the medical record.
    4. DO NOT search for or identify unrecognized abbreviations.
    5. Focus ONLY on creating a meaningful summary using the abbreviations provided.
    
    STRICT RULES:
    1. ALWAYS set "responsetype" to "summary"
    2. ALWAYS set "unrecognized_abbreviations" to null
    3. Provide a detailed, informative summary in the "summary" field
    4. Use the provided abbreviations to interpret medical terms correctly
    5. Include relevant medical information such as:
       - Patient symptoms and complaints
       - Medical history and findings
       - Diagnostic results
       - Treatment plans or recommendations
       - Follow-up instructions
    
    IMPORTANT:
    - NEVER change responsetype from "summary"
    - NEVER populate unrecognized_abbreviations field
    - Focus on creating a useful medical summary
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
                if "unrecognizedAbbreviation" not in raw_data:
                    raw_data["unrecognizedAbbreviation"] = None
                return FinalResponse.model_validate(raw_data)
            except Exception as e:
                raise ValueError(f"Failed to parse LLM response: {str(e)}. Raw response: {response.content}")

    def generate_medical_summary(self, record_text: str, user_abbreviations: Optional[List[AbbreviationDefinition]] = None) -> FinalResponse:
        """
        Generate a comprehensive summary of a medical record using provided abbreviations.
        This method focuses only on summarization and does not check for unrecognized abbreviations.
        
        Args:
            record_text (str): The medical record text to summarize
            user_abbreviations (Optional[List[AbbreviationDefinition]]): User-defined abbreviations to use in summary generation
            
        Returns:
            FinalResponse: Always returns responsetype="summary" with comprehensive summary text
        """
        # Convert HTML to markdown for better processing
        record_text = self.generate_summary(record_text)
        abbreviation_context = ""
        
        # Build personalized abbreviations context if provided
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
        
        # Use the summary-only prompt template
        _prompt = self.summary_prompt_template.format(record_text=full_context)
        response = self.llm.invoke(_prompt)
        
        try:
            # Parse directly into FinalResponse
            return FinalResponse.model_validate_json(response.content)
        except Exception as e:
            # Fallback parsing
            try:
                raw_data = json.loads(response.content)
                # Ensure required fields exist for summary response
                if "summary" not in raw_data:
                    raw_data["summary"] = ""
                # Force summary response type
                raw_data["responsetype"] = "summary"
                raw_data["unrecognizedAbbreviation"] = None
                return FinalResponse.model_validate(raw_data)
            except Exception as e:
                raise ValueError(f"Failed to parse LLM response: {str(e)}. Raw response: {response.content}")