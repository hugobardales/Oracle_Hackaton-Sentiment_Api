package com.hackathon.sentimentapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ANALYSIS_LOGS") // Nombre de la tabla en Oracle
@Data // Lombok genera getters/setters automágicamente
@NoArgsConstructor
public class AnalysisLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle 12c+ soporta Identity
    private Long id;

    @Column(length = 1000, nullable = false)
    private String originalText;

    @Column(length = 20, nullable = false)
    private String sentiment;

    @Column(nullable = false)
    private Double confidenceScore;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Constructor personalizado para facilitar la creación
    public AnalysisLog(String text, String sentiment, Double score) {
        this.originalText = text;
        this.sentiment = sentiment;
        this.confidenceScore = score;
        this.createdAt = LocalDateTime.now();
    }
}