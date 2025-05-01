package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.exception.BadRequestException;
import com.antdevrealm.jobpilot.exception.FieldValidationException;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> error = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        Map<String, Object> error = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        if (ex.getValidValues() != null) {
            error.put("validValues", ex.getValidValues());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> Map.of(
                        "field", fieldError.getField(),
                        "message", Objects.requireNonNull(fieldError.getDefaultMessage())
                ))
                .toList();

        Map<String, Object> body = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<Map<String, Object>> handleFieldUniqueConstraintError(FieldValidationException ex, HttpServletRequest request) {
        List<Map<String, String>> errors = List.of(Map.of(
                "field", ex.getField(),
                "message", ex.getMessage()
        ));

        Map<String, Object> body = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Field validation error",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private Map<String, Object> buildErrorResponse(HttpStatus status, String message, String path, List<Map<String, String>> fieldErrors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            body.put("errors", fieldErrors);
        }
        return body;
    }
}
