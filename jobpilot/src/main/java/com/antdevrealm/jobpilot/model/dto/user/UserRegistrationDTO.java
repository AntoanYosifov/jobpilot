package com.antdevrealm.jobpilot.model.dto.user;

public record UserRegistrationDTO(
    String firstName,
    String lastName,
    String email,
    String password
) {}
