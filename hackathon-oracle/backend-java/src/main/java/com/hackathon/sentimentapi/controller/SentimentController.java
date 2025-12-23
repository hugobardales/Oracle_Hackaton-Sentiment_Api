package com.hackathon.sentimentapi.controller;

import com.hackathon.sentimentapi.dto.AnalyzeRequest;
import com.hackathon.sentimentapi.dto.AnalyzeResponse;
import com.hackathon.sentimentapi.model.AnalysisLog;
import com.hackathon.sentimentapi.service.SentimentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling sentiment analysis requests.
 * Provides endpoints for analyzing text and retrieving the history of analyses.
 */
@RestController
@RequestMapping("/api/v1") // Sets the base path for all endpoints in this controller to "/api/v1".
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required final fields (e.g., SentimentService).
@Tag(name = "Sentiment Analysis", description = "Endpoints principales para la Hackathon")
@CrossOrigin(origins = "*") // ¡IMPORTANTE PARA EL MVP! Permite que Streamlit se conecte sin bloqueos
public class SentimentController {

    /**
     * The service layer that contains the business logic for sentiment analysis.
     */
    private final SentimentService service;

    /**
     * Endpoint to analyze the sentiment of a given text.
     * The request body is expected to contain the text to be analyzed.
     * The result of the analysis is saved to the database.
     *
     * @param request The request DTO containing the text to analyze. Must be valid.
     * @return A ResponseEntity containing the analysis response with sentiment and polarity.
     */
    @PostMapping("/analyze")
    @Operation(summary = "Analizar Texto", description = "Envía un texto a la IA y guárdalo en Oracle")
    public ResponseEntity<AnalyzeResponse> analyze(@Valid @RequestBody AnalyzeRequest request) {
        return ResponseEntity.ok(service.analyzeText(request));
    }

    /**
     * Endpoint to retrieve a paginated history of past analyses.
     * Allows for sorting and pagination of the results.
     *
     * @param pageable Object containing pagination information (page, size, sort) provided by Spring Web.
     *                 Defaults to page 0, size 10, and sorted by 'createdAt' in descending order.
     * @return A ResponseEntity containing a Page of AnalysisLog entries.
     */
    @GetMapping("/history")
    @Operation(summary = "Ver Historial", description = "Obtén los últimos análisis paginados")
    public ResponseEntity<Page<AnalysisLog>> getHistory(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.getHistory(pageable));
    }
}
