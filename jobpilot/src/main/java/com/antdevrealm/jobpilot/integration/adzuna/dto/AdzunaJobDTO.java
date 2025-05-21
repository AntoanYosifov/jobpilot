package com.antdevrealm.jobpilot.integration.adzuna.dto;

import java.time.ZonedDateTime;

public record AdzunaJobDTO(
        String id,
        String title,
        Company company,
        Location location,
        String description,
        String redirect_url,
        ZonedDateTime created,
        Double latitude,    // use wrapper types
        Double longitude
) {
    public record Company(String display_name) {}
    public record Location(String display_name) {}
}
