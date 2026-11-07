package org.example.handler;

public record ErrorResponse(
        String code,
        String message,
        String correlationId
) {}