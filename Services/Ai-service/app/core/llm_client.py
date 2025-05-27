from langchain.chat_models import ChatOpenAI
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
            model="deepseek-chat",
            temperature=0.6,
            openai_api_key=os.getenv("DEEPSEEK_API_KEY"),
            base_url="https://api.deepseek.com/v1",
            response_format={ "type": "json_object" }
        )