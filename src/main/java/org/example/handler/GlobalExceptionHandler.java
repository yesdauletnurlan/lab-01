package org.example.handler;

import org.example.domain.DuplicateIssue;
import org.example.domain.IssueNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .findFirst()
                .orElse("validation failed");
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("VALIDATION", message, correlationId()));
    }

    @ExceptionHandler(IssueNotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(IssueNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", ex.getMessage(), correlationId()));
    }

    @ExceptionHandler(DuplicateIssue.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateIssue ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("DUPLICATE", ex.getMessage(), correlationId()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("VALIDATION", ex.getMessage(), correlationId()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL", "unexpected error", correlationId()));
    }

    private String correlationId() {
        return UUID.randomUUID().toString();
    }
}