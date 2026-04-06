package com.github.issue.analyzer.InsightAI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Map<String, String>> handleWebClientException(WebClientResponseException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        String message;

        if (status == HttpStatus.NOT_FOUND) {
            message = "GitHub repository not found. Please check repo name.";
        } else if (status == HttpStatus.FORBIDDEN) {
            message = "GitHub API rate limit exceeded or access denied.";
        } else if (status == HttpStatus.UNAUTHORIZED) {
            message = "Unauthorized access to GitHub API.";
        } else {
            message = "Error while calling external API: " + ex.getMessage();
        }

        return ResponseEntity
                .status(status)
                .body(Map.of(
                        "error", "External API Error",
                        "message", message
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "message", "An unexpected error occurred. Please try again later."
                ));
    }
}
