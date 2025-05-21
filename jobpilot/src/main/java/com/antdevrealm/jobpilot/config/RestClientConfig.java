package com.antdevrealm.jobpilot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient adzunaRestClient(AdzunaPropertiesConfig props) {
        return RestClient.builder()
                .baseUrl(props.getBaseUrl() + "/" + props.getCountry())
//                .defaultUriVariables(
//                        Map.of("app_id", props.getAppId(),
//                                "app_key", props.getAppKey())
//                )
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
