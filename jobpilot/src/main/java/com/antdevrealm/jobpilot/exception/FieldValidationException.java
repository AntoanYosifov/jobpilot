package com.antdevrealm.jobpilot.exception;

import lombok.Getter;

public class FieldValidationException extends RuntimeException{
    @Getter
    private final String field;
    private final String message;

    public FieldValidationException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
