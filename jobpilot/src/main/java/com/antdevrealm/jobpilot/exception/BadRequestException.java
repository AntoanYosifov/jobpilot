package com.antdevrealm.jobpilot.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException{
    private final List<String> validValues;

    public BadRequestException(String message, List<String> validValues) {
        super(message);
        this.validValues = validValues;
    }

}
