# Mecânico Já - Backend 

Este diretório contém a estrutura de microsserviços para o projeto Mecânico Já.

## Estrutura de Pastas
- `core-api-java/`: API principal desenvolvida em Java (Spring Boot) contendo as regras de negócio, autenticação e banco de dados PostgreSQL/PostGIS.
- `ia-chat-python/`: Microsserviço em Python (FastAPI) lidando com Inteligência Artificial (Google Gemini) e comunicação assíncrona/WebSockets via Redis.

## Infraestrutura Local
Para iniciar o banco de dados (PostgreSQL + PostGIS) e o Redis:
```bash
docker-compose up -d
```
