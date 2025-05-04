package com.antdevrealm.jobpilot.exception.dto;


public record FieldValidationErrorDTO(
        String field,
        String message
) { }
