package com.hackathon.sentimentapi.repository;

import com.hackathon.sentimentapi.model.AnalysisLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentimentRepository extends JpaRepository<AnalysisLog, Long> {
    // Aquí podríamos agregar métodos mágicos como:
    // List<AnalysisLog> findBySentiment(String sentiment);
    // Pero para el MVP, los métodos por defecto (save, findAll) son suficientes.
}