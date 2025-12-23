import streamlit as st
import requests
import pandas as pd
import os

# Configuración de la página
st.set_page_config(page_title="Oracle Sentiment Analyzer", layout="centered")

# URL del Backend Java
API_URL = os.getenv("BACKEND_URL", "http://backend-java:8080")

st.title("🤖 SentimentAPI Enterprise")
st.caption("Powered by Oracle Database 23c & Java 21")

# 1. Formulario de Entrada
with st.form("analyze_form"):
    text_input = st.text_area("Ingresa un comentario (Cliente/Usuario):", height=100)
    submitted = st.form_submit_button("Analizar Sentimiento")

# 2. Lógica al presionar el botón
if submitted and text_input:
    try:
        with st.spinner('Procesando con IA...'):
            response = requests.post(f"{API_URL}/api/v1/analyze", json={"text": text_input})

        if response.status_code == 200:
            data = response.json()
            sentiment = data.get("sentiment", "UNKNOWN")
            score = data.get("score", 0.0)

            col1, col2 = st.columns(2)
            with col1:
                st.metric("Sentimiento", sentiment)
            with col2:
                st.progress(score, text=f"Confianza: {score:.2%}")

            if sentiment == "POSITIVE":
                st.success("¡Comentario Positivo! 😃")
            elif sentiment == "NEGATIVE":
                st.error("Comentario Negativo 😠")
            else:
                st.info("Comentario Neutro 😐")
        else:
            st.error(f"Error del servidor: {response.status_code}")

    except requests.exceptions.ConnectionError:
        st.error("⚠️ No se pudo conectar con el Backend Java. ¿Está encendido el contenedor?")

st.divider()

# 3. Historial
st.subheader("📜 Historial Reciente (Live)")
if st.button("Refrescar Datos"):
    try:
        hist_resp = requests.get(f"{API_URL}/api/v1/history")
        if hist_resp.status_code == 200:
            history_data = hist_resp.json().get('content', [])
            if history_data:
                df = pd.DataFrame(history_data)
                st.dataframe(df, use_container_width=True)
            else:
                st.info("Aún no hay análisis registrados.")
    except:
        st.warning("Backend no disponible para historial.")