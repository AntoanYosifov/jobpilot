package com.antdevrealm.jobpilot.integration.adzuna.dto;

import java.util.List;

public record AdzunaResponseDTO(
        int count,
        List<AdzunaJobDTO> results
) {}
