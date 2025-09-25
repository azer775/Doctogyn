from typing import List, Optional
from pydantic import BaseModel, Field
from enum import Enum

class ResponseType(str, Enum):
    SUMMARY = "SUMMARY"
    ABBREVIATION_ISSUE = "ABBREVIATION_ISSUE"

class AbbreviationDefinition(BaseModel):
    abbreviation: str
    meaning: str

class UnrecognizedAbbreviation(BaseModel):
    abbreviation: str
    possibleMeanings: List[str]
class SummaryRequest(BaseModel):
    text: str
    abbreviations: Optional[List[AbbreviationDefinition]] = None
class FinalResponse(BaseModel):
    '''how to handle the output of the LLM'''
    responsetype: ResponseType
    unrecognizedAbbreviation: Optional[List[UnrecognizedAbbreviation]] = Field(
        description="List of abbreviations and their possible meanings the model could not understand"
    )
    summary: str = Field(description="A concise summary of the medical text")