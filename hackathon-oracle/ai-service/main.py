from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from transformers import pipeline

# 1. Iniciamos la App
app = FastAPI(title="Sentiment AI Microservice")

# 2. Definimos el modelo de datos (Input)
class TextRequest(BaseModel):
    text: str

# 3. Cargamos la IA al iniciar (Variable global)
# Usamos un modelo ligero multilingüe perfecto para hackathons
print("⏳ Cargando modelo de IA... esto puede tardar la primera vez.")
sentiment_pipeline = pipeline(
    "sentiment-analysis",
    model="lxyuan/distilbert-base-multilingual-cased-sentiments-student"
)
print("✅ Modelo cargado correctamente.")

@app.post("/predict")
def predict_sentiment(request: TextRequest):
    try:
        # Limitamos texto para no saturar memoria
        if len(request.text) > 512:
            raise HTTPException(status_code=400, detail="Texto muy largo (max 512 caracteres)")

        # Ejecutamos la IA
        result = sentiment_pipeline(request.text)[0]

        # HuggingFace devuelve: {'label': 'positive', 'score': 0.95}
        # Mapeamos a MAYÚSCULAS para Java
        return {
            "sentiment": result['label'].upper(),
            "confidence": float(result['score'])
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/health")
def health_check():
    return {"status": "ok", "model_loaded": True}