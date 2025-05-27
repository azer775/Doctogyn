from pydantic import BaseModel

class BloodTestResult(BaseModel):
    id: str
    value: str