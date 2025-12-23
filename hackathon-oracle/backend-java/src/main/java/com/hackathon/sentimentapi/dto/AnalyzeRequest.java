package com.hackathon.sentimentapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO para recibir el texto del usuario
public record AnalyzeRequest(
        @NotBlank(message = "El texto no puede estar vacío")
        @Size(max = 512, message = "El texto no puede superar los 512 caracteres")
        String text
) {}