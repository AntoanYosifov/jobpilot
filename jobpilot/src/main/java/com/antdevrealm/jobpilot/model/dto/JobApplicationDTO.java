package com.antdevrealm.jobpilot.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobApplicationDTO(
        @NotBlank
        @Size(min = 2, max = 100)
        String company,
        @NotBlank
        @Size(min = 2, max = 100)
        String position,
        @NotNull
        String status
) {
}
