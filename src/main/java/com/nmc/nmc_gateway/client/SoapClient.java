/*
package com.nmc.nmc_gateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static com.nmc.nmc_gateway.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class SoapClient {

    private final WebClient webClient;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${tatmeen.base-url}")
    private String baseURL;

    @Value("${tatmeen.api-key}")
    private String apiKey;

    public SoapClient(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(baseURL).build();
    }

    public String sendRequest(String url, String bearerToken, String body){
        return webClient.post()
            .uri(url)
            .header(API_KEY, apiKey)
            .header(AUTHORIZATION, BEARER + bearerToken)
            .contentType(MediaType.valueOf(MediaType.APPLICATION_XML_VALUE))
            .bodyValue(body)
            .retrieve().bodyToMono(String.class)
            .block();
    }

}
*/
