package com.antdevrealm.jobpilot.model.dto.jobposting;

import java.time.ZonedDateTime;

public record JobPostingResponseDTO(
        Long id,
        String title,
        String companyName,
        String locationName,
        String description,
        String redirect_url,
        ZonedDateTime externalCreatedAt,
// ----------------------------------------------------------------
// TODO(front-end): latitude & longitude will be used by the UI
//     to plot job locations on a map. These stay nullable here
//     until the front end implements mapping/geo-features.
// ----------------------------------------------------------------
        Double latitude,
        Double longitude
) {}
