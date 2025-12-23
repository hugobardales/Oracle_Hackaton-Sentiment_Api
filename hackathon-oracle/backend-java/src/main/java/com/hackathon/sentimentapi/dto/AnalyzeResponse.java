package com.hackathon.sentimentapi.dto;

// DTO para responder al usuario (JSON final)
public record AnalyzeResponse(
        String sentiment, // POSITIVE, NEGATIVE, NEUTRAL
        double score,     // 0.98
        String processedAt // Fecha bonita para mostrar
) {}