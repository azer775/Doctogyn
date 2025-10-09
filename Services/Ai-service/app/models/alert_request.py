from pydantic import BaseModel
class AlertRequest(BaseModel):
    resume: str
    consultation: str