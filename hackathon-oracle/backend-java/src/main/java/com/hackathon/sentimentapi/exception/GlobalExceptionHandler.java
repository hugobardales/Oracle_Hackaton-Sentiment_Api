package com.hackathon.sentimentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Error cuando Python está apagado o no responde
    @ExceptionHandler(ResourceAccessException.class)
    public ProblemDetail handleConnectionError(ResourceAccessException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE,
                "El servicio de Inteligencia Artificial no está respondiendo. ¿Está encendido el contenedor de Python?"
        );
        problem.setTitle("AI Service Unreachable");
        problem.setType(URI.create("https://hackathon-oracle/errors/ai-down"));
        return problem;
    }

    // 2. Error genérico (Cualquier otra cosa)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericError(Exception e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
        problem.setTitle("Internal Server Error");
        return problem;
    }
}