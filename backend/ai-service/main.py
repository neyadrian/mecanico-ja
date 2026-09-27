from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from pydantic import BaseModel
from typing import Dict, List
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

# ==========================================
# FASE 4: WEBSOCKETS (CHAT EM TEMPO REAL)
# ==========================================

class ConnectionManager:
    def __init__(self):
        # Guarda as conexões separadas por "sala" (ID do chamado de socorro)
        self.active_connections: Dict[str, List[WebSocket]] = {}

    async def connect(self, websocket: WebSocket, chamado_id: str):
        await websocket.accept()
        if chamado_id not in self.active_connections:
            self.active_connections[chamado_id] = []
        self.active_connections[chamado_id].append(websocket)

    def disconnect(self, websocket: WebSocket, chamado_id: str):
        self.active_connections[chamado_id].remove(websocket)
        if not self.active_connections[chamado_id]:
            del self.active_connections[chamado_id]

    async def broadcast(self, message: str, chamado_id: str):
        if chamado_id in self.active_connections:
            for connection in self.active_connections[chamado_id]:
                await connection.send_text(message)

manager = ConnectionManager()

@app.websocket("/ws/chat/{chamado_id}")
async def websocket_endpoint(websocket: WebSocket, chamado_id: str):
    await manager.connect(websocket, chamado_id)
    try:
        while True:
            # Espera receber uma mensagem de texto do aplicativo
            data = await websocket.receive_text()
            # Envia a mensagem para a outra pessoa na mesma "sala"
            await manager.broadcast(f"{data}", chamado_id)
    except WebSocketDisconnect:
        manager.disconnect(websocket, chamado_id)
        await manager.broadcast("Sistema: Um usuário saiu do chat de emergência.", chamado_id)