package com.antdevrealm.jobpilot.integration.adzuna;


import com.antdevrealm.jobpilot.config.AdzunaPropertiesConfig;
import com.antdevrealm.jobpilot.exception.ExternalServiceException;
import com.antdevrealm.jobpilot.integration.adzuna.dto.AdzunaResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;


@Component
public class AdzunaJobClient {
    private final RestClient restClient;
    private final AdzunaPropertiesConfig props;


    public AdzunaJobClient(RestClient restClient, AdzunaPropertiesConfig props) {
        this.restClient = restClient;
        this.props = props;
    }

    public AdzunaResponseDTO fetchJobs(int page) {

        try {
            return restClient.get()
                    .uri(b -> b
                                    .path("/search/" + page)
                            .queryParam("app_id",            props.getAppId())
                            .queryParam("app_key",           props.getAppKey())
                            .queryParam("what",              "developer")
                            .queryParam("results_per_page",  props.getDefaultResultsPerPage())
                            .queryParam("max_days_old",      props.getMaxDaysOld())
                            .queryParam("sort_dir",      props.getSortDir())
                            .queryParam("sort_by",           props.getSortBy())
                            .build()
                    )
                    .retrieve()
                    .toEntity(AdzunaResponseDTO.class)
                    .getBody();

        } catch (RestClientException ex) {
            throw new ExternalServiceException(
                    "Failed to fetch jobs from Adzuna", ex
            );
        }
    }
}
