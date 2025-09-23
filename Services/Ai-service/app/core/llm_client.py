from langchain_community.chat_models import ChatOpenAI
from dotenv import load_dotenv
import os

load_dotenv()
class LLMClient:
    _instance = None

    @staticmethod
    def get_instance():
        if LLMClient._instance is None:
            LLMClient()
        return LLMClient._instance
     

    def __init__(self):
        if LLMClient._instance is not None:
            raise Exception("LLMClient is a singleton!")
        LLMClient._instance = ChatOpenAI(
            model="gpt-4.1",
            temperature=0.6,
            openai_api_key=os.getenv("OPENAI_API_KEY"),
            model_kwargs={
        "response_format": {
            "type": "json_schema",
            "json_schema": {
                "name": "MedicalDocuments",
                "schema": {
                    "type": "object",
                    "properties": {
                        "documents": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "biologies": {
                                        "type": "array",
                                        "items": {
                                            "type": "object",
                                            "properties": {
                                                "date": {"type": "string", "format": "date"},
                                                "type": {"type": "integer"},
                                                "value": {"type": "number"},
                                                "interpretation": {"type": "integer"},
                                                "comment": {"type": "string"},
                                                "consultationId": {"type": "integer"}
                                            },
                                            "required": ["date", "type", "value", "interpretation"]
                                        }
                                    },
                                    "serologies": {
                                        "type": "array",
                                        "items": {
                                            "type": "object",
                                            "properties": {
                                                "date": {"type": "string", "format": "date"},
                                                "type": {"type": "integer"},
                                                "value": {"type": "number"},
                                                "interpretation": {"type": "integer"},
                                                "comment": {"type": "string"},
                                                "consultationId": {"type": "integer"}
                                            },
                                            "required": ["date", "type", "value", "interpretation"]
                                        }
                                    },
                                    "bacteriologies": {
                                        "type": "array",
                                        "items": {
                                            "type": "object",
                                            "properties": {
                                                "date": {"type": "string", "format": "date"},
                                                "type": {"type": "integer"},
                                                "germs": {"type": "array", "items": {"type": "integer"}},
                                                "interpretation": {"type": "integer"},
                                                "comment": {"type": "string"},
                                                "consultationId": {"type": "integer"}
                                            },
                                            "required": ["date", "type", "germs", "interpretation"]
                                        }
                                    },
                                    "bloodgroups": {
                                        "type": "array",
                                        "items": {
                                            "type": "object",
                                            "properties": {
                                                "date": {"type": "string", "format": "date"},
                                                "type": {"type": "integer"},
                                                "comment": {"type": "string"},
                                                "consultationId": {"type": "integer"}
                                            },
                                            "required": ["date", "type"]
                                        }
                                    },
                                    "radiologies": {
                                        "type": "array",
                                        "items": {
                                            "type": "object",
                                            "properties": {
                                                "date": {"type": "string", "format": "date"},
                                                "type": {"type": "integer"},
                                                "conclusion": {"type": "string"},
                                                "comment": {"type": "string"},
                                                "consultationId": {"type": "integer"}
                                            },
                                            "required": ["type", "conclusion"]
                                        }
                                    },
                                    "spermAnalyses": {
                                        "type": "array",
                                        "items": {
                                            "type": "object",
                                            "properties": {
                                                "date": {"type": "string", "format": "date"},
                                                "abstinence": {"type": "integer"},
                                                "ph": {"type": "number"},
                                                "volume": {"type": "number"},
                                                "concentration": {"type": "number"},
                                                "progressivemobility": {"type": "number"},
                                                "totalmotility": {"type": "number"},
                                                "totalcount": {"type": "integer"},
                                                "roundcells": {"type": "integer"},
                                                "leukocytes": {"type": "integer"},
                                                "morphology": {"type": "number"},
                                                "norm": {"type": "string"},
                                                "vitality": {"type": "number"},
                                                "tms": {"type": "number"},
                                                "consultationId": {"type": "integer"}
                                            },
                                            "required": ["date", "abstinence", "volume", "concentration", "consultationId"]
                                        }
                                    }
                                },
                                "required": ["biologies", "serologies", "bacteriologies", "bloodgroups", "radiologies", "spermAnalyses"]
                            }
                        }
                    },
                    "required": ["documents"]
                }
            }
        }
    }

        )