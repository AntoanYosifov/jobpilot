package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.exception.BadRequestException;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound (ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(com.antdevrealm.jobpilot.exception.BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("error", ex.getMessage());
        error.put("validStatues: ", ex.getValidValues());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
