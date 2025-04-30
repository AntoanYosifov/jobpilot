package com.antdevrealm.jobpilot.model.dto.user;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email
) {}
