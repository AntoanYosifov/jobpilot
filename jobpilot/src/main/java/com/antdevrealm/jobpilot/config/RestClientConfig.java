package com.antdevrealm.jobpilot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient adzunaRestClient(AdzunaPropertiesConfig props) {
        return RestClient.builder()
                .baseUrl(props.getBaseUrl() + "/" + props.getCountry())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
