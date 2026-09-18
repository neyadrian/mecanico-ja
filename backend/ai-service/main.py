from fastapi import FastAPI
from pydantic import BaseModel
from google import genai

app = FastAPI(title="Mecânico Já - AI Service")

# Inicializa o cliente do Gemini com a sua chave
GEMINI_API_KEY = "CHAVE_REMOVIDA_POR_SEGURANCA"
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

    # Chama o cérebro do Google Gemini (usando o modelo flash, que é o mais rápido)
    response = client.models.generate_content(
        model='gemini-3.6-flash',
        contents=prompt,
    )

    # Devolve a resposta inteligente
    return {
        "pre_diagnostico": response.text
    }