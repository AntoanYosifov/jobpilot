package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.exception.FieldValidationException;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.exception.dto.ErrorResponseDTO;
import com.antdevrealm.jobpilot.exception.dto.FieldValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> onConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest req) {

        List<FieldValidationErrorDTO> fieldErrors = ex.getConstraintViolations().stream()
                .map(cv -> new FieldValidationErrorDTO(
                        cv.getPropertyPath().toString(),
                        cv.getMessage()))
                .toList();

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Request parameters validation failed",
                req.getRequestURI(),
                fieldErrors
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> onNotFound(ResourceNotFoundException ex,
                                                       HttpServletRequest req) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                req.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<ErrorResponseDTO> onFieldValidation(FieldValidationException ex,
                                                              HttpServletRequest req) {

        List<FieldValidationErrorDTO> fieldErrors = List.of(
                new FieldValidationErrorDTO(ex.getField(), ex.getMessage())
        );
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Field validation error", req.getRequestURI(), fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> onAllErrors(Exception ex,
                                                        HttpServletRequest req) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(), req.getRequestURI(), null);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {

        List<FieldValidationErrorDTO> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new FieldValidationErrorDTO(fe.getField(), fe.getDefaultMessage()))
                .toList();

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponseDTO body = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                path,
                fieldErrors
        );

        return ResponseEntity.badRequest().body(body);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(HttpStatus status,
                                                                String message,
                                                                String path,
                                                                List<FieldValidationErrorDTO> fieldErrors) {
        ErrorResponseDTO body = new ErrorResponseDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                fieldErrors
        );

        return ResponseEntity.status(status).body(body);
    }
}
