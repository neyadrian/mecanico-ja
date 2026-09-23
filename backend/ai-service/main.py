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

class SintomasRequest(BaseModel):
    sintomas: str

@app.post("/api/ia/analisar")
async def analisar_sintomas(request: SintomasRequest):
    
    # Engenharia de Prompt: Dando uma "personalidade" para a nossa IA
    prompt = f"""
    Você é um mecânico automotivo especialista de alto nível. 
    Um motorista relatou o seguinte problema no veículo dele: '{request.sintomas}'.
    
    Faça um pré-diagnóstico técnico, curto e direto ao ponto (máximo de 3 linhas), 
    sugerindo quais sistemas ou peças podem estar com defeito para orientar o mecânico que vai atender o chamado.
    """
    
    # Chama o cérebro do Google Gemini usando a nova API de Interações
    interaction = client.interactions.create(
        model='gemini-3.7-flash',
        input=prompt,
    )
    
    # Devolve a resposta inteligente
    return {
        "pre_diagnostico": interaction.output_text
    }