package com.antdevrealm.jobpilot.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 8, max = 20)
        String password,
        @NotBlank
        @Size(min = 2, max = 100)
        String firstName,
        @NotBlank
        @Size(min = 2, max = 100)
        String lastName

) {
}
