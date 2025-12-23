package com.hackathon.sentimentapi.service;

import com.hackathon.sentimentapi.dto.AnalyzeRequest;
import com.hackathon.sentimentapi.dto.AnalyzeResponse;
import com.hackathon.sentimentapi.model.AnalysisLog;
import com.hackathon.sentimentapi.repository.SentimentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;

import java.time.format.DateTimeFormatter;

@Service
public class SentimentService {

    private final SentimentRepository repository;
    private final RestClient restClient;

    // Inyectamos la URL desde application.yaml o Docker (Variable de Entorno)
    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public SentimentService(SentimentRepository repository, RestClient.Builder restClientBuilder) {
        this.repository = repository;
        this.restClient = restClientBuilder.build();
    }

    // Método principal
    public AnalyzeResponse analyzeText(AnalyzeRequest request) {

        // 1. Llamar a Python (AI)
        // Definimos una clase interna temporal para mapear la respuesta de Python
        record PythonResponse(String sentiment, Double confidence) {
        }

        PythonResponse aiResult = restClient.post()
                .uri(aiServiceUrl + "/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request) // Enviamos {"text": "..."}
                .retrieve()
                .body(PythonResponse.class);

        if (aiResult == null) {
            throw new RuntimeException("La IA no devolvió respuesta");
        }

        // 2. Guardar en Oracle (Persistencia)
        AnalysisLog log = new AnalysisLog(
                request.text(),
                aiResult.sentiment(),
                aiResult.confidence());
        repository.save(log);

        // 3. Responder al usuario
        return new AnalyzeResponse(
                log.getSentiment(),
                log.getConfidenceScore(),
                log.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    // Método para historial
    public Page<AnalysisLog> getHistory(Pageable pageable) {
        return repository.findAll(pageable);
    }
}