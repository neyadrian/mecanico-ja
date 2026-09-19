from fastapi import FastAPI
from pydantic import BaseModel
from google import genai
import os
from dotenv import load_dotenv

# Carrega as senhas escondidas no arquivo .env
load_dotenv()

app = FastAPI(title="Mecânico Já - AI Service")

# Puxa a chave de forma segura e injeta no Gemini
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
client = genai.Client(api_key=GEMINI_API_KEY)

# ... (o resto do código continua igualzinho)