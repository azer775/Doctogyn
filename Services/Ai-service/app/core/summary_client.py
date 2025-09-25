from langchain_community.chat_models import ChatOpenAI
from dotenv import load_dotenv
import os

load_dotenv()
class SummaryClient:
    _instance = None

    @staticmethod
    def get_instance():
        if SummaryClient._instance is None:
            SummaryClient()
        return SummaryClient._instance
     

    def __init__(self):
        if SummaryClient._instance is not None:
            raise Exception("SummaryClient is a singleton!")
        SummaryClient._instance = ChatOpenAI(
            model="gpt-4.1",
            temperature=0.6,
            openai_api_key=os.getenv("OPENAI_API_KEY"),
            model_kwargs={
        "response_format": {
            "type": "json_schema",
            "json_schema": {
                "name": "SummaryResponse",
                "schema": {
                    "type": "object",
                    "properties": {
                        "responsetype": {
                            "type": "string",
                            "enum": ["SUMMARY", "ABBREVIATION_ISSUE"]
                        },
                        "summary": {
                            "type": "string"
                        },
                        "unrecognizedAbbreviation": {
                            "type": ["array", "null"],
                            "items": {
                                "type": "object",
                                "properties": {
                                    "abbreviation": {"type": "string"},
                                    "possibleMeanings": {
                                        "type": "array",
                                        "items": {"type": "string"}
                                    }
                                },
                                "required": ["abbreviation", "possibleMeanings"],
                                "additionalProperties": False
                            }
                        }
                    },
                    "required": ["responsetype", "summary", "UnrecognizedAbbreviation"],
                    "additionalProperties": False
                }
            }
        }
    }

        )