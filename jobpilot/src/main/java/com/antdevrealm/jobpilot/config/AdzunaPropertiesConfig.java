package com.antdevrealm.jobpilot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "adzuna.api")
public class AdzunaPropertiesConfig {
    private String baseUrl;
    private String appId;
    private String appKey;
    private String country;
    private int defaultResultsPerPage;
    private int maxDaysOld;
    private String sortDir;
    private String sortBy;
}
