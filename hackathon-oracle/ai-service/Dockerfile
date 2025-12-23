FROM python:3.10-slim

WORKDIR /app

COPY requirements.txt .

# Aumentamos el timeout para evitar errores si el internet está lento
RUN pip install --no-cache-dir --default-timeout=1000 -r requirements.txt

COPY main.py .

# CAMBIO CLAVE: Usamos el puerto 8000 para coincidir con Java
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]