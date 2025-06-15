package com.antdevrealm.jobpilot.model.dto.jobapplication;

import jakarta.validation.constraints.NotNull;

public record JobApplicationDTO(
       @NotNull Long jobPostingId
) {}
