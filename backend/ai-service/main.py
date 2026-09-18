from fastapi import FastAPI
from pydantic import BaseModel

# Inicializa o servidor web
app = FastAPI(title="Mecânico Já - AI Service")

# Define o formato do JSON que o Java vai mandar para o Python
class SintomasRequest(BaseModel):
    sintomas: str

# Rota que o Java vai chamar
@app.post("/api/ia/analisar")
async def analisar_sintomas(request: SintomasRequest):

    sintoma_do_motorista = request.sintomas

    # TODO: Na próxima etapa vamos plugar o Google Gemini de verdade aqui!
    # Por enquanto, criamos uma resposta simulada para testar a comunicação.
    resposta_ia = f"Análise simulada: O sintoma '{sintoma_do_motorista}' geralmente indica problemas na injeção eletrônica ou velas desgastadas. Recomenda-se escaneamento OBD2."

    return {
        "pre_diagnostico": resposta_ia
    }