from pydantic import BaseModel
class AlertResponse(BaseModel):
    alerts: list[str]