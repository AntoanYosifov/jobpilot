package com.antdevrealm.jobpilot.integration.adzuna;


import com.antdevrealm.jobpilot.config.AdzunaPropertiesConfig;
import com.antdevrealm.jobpilot.exception.ExternalServiceException;
import com.antdevrealm.jobpilot.integration.adzuna.dto.AdzunaJobDTO;
import com.antdevrealm.jobpilot.integration.adzuna.dto.AdzunaResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class AdzunaJobClient {
    private final RestClient restClient;
    private final AdzunaPropertiesConfig props;

    public AdzunaJobClient(RestClient restClient, AdzunaPropertiesConfig props) {
        this.restClient = restClient;
        this.props = props;
    }

    public List<AdzunaJobDTO> fetchJobs(int page) {

        try {
            AdzunaResponseDTO resp = restClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/search/{page}")
                                    .queryParam("app_id", "{app_id}")
                                    .queryParam("app_key", "{app_key}")
                                    .queryParam("what", "developer")
                                    .queryParam("results_per_page", props.getDefaultResultsPerPage())
                                    .build(Map.of("page", page))
                    )
                    .retrieve()
                    .toEntity(AdzunaResponseDTO.class)
                    .getBody();
            return (resp != null ? resp.results() : Collections.emptyList());
        } catch (RestClientException ex) {
            throw new ExternalServiceException(
                    "Failed to fetch jobs from Adzuna", ex
            );
        }
    }
}
