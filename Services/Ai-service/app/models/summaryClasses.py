from typing import List, Optional
from pydantic import BaseModel, Field
from enum import Enum

class SummaryRequest(BaseModel):
    text: str
class ResponseType(str, Enum):
    SUMMARY = "summary"
    ABBREVIATION_ISSUE = "abbreviation_issue"
class SummaryOutput(BaseModel):
    '''how to handle the summary of the LLM'''
    summary: str = Field(description="A concise summary of the medical text")
    class Config:
        strict = True


class AbbreviationIssue(BaseModel):
    '''how to handle the abbreviations of the LLM'''
    unrecognized_abbreviations: Optional[List[str]] = Field(
        description="List of abbreviations the model could not understand"
    )
    class Config:
        strict = True
class AbbreviationDefinition(BaseModel):
    abbreviation: str
    meaning: str

class AnalysisRequest(BaseModel):
    text: str
    abbreviations: Optional[List[AbbreviationDefinition]] = None
class unrecognized_abbreviation(BaseModel):
    Abbreviation: str
    PossibleMeanings: List[str]
class FinalResponse(BaseModel):
    '''how to handle the output of the LLM'''
    responsetype: ResponseType
    unrecognized_abbreviations: Optional[List[unrecognized_abbreviation]] = Field(
        description="List of abbreviations and their possible meanings the model could not understand"
    )
    summary: str = Field(description="A concise summary of the medical text")