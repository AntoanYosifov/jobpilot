package com.antdevrealm.jobpilot.model.dto;

public record JobApplicationResponseDTO(
        Long id,
        String company,
        String position,
        String status,
        String appliedOn,
        String feedback
) {}
