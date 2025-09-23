from fastapi import FastAPI
from app.Apis import Extraction
from fastapi.middleware.cors import CORSMiddleware
import py_eureka_client.eureka_client as eureka_client
from dotenv import load_dotenv
import os

load_dotenv()
app = FastAPI()
@app.on_event("startup")
async def startup_event():
    await eureka_client.init_async(
        eureka_server="http://localhost:8761/eureka",
        app_name="fastapi-service",
        instance_port=8000, 
        instance_host="localhost"
    ) 


app.include_router(Extraction.router, prefix="/pdf", tags=["PDF"]) 