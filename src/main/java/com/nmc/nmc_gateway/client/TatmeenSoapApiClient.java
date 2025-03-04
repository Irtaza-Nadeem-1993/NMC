package com.nmc.nmc_gateway.client;

import static com.nmc.nmc_gateway.utils.Constants.*;

import com.nmc.nmc_gateway.client.xmlBody.TatmeenReceiveCall;
import com.nmc.nmc_gateway.service.AuthService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TatmeenSoapApiClient {

    @Value("${tatmeen.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private final AuthService authService;

    public String sendSoapRequest() throws Exception {
        String url = TATMEEN_BASE_URL + TATMEEN_SCP_URL; // SOAP API URL

        String requestXmlChanged = TatmeenReceiveCall.createSOAPRequestMyWay(
            List.of("xxxxxxxx.123456789", "xxxxxxxx.123456799"),
            List.of("xxxxxxxx.123456889", "xxxxxxxx.123456890"),
            "xxxxxxxx.5555.0"
        );

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set(API_KEY, apiKey);
        headers.set(AUTHORIZATION, BEARER + authService.getAccessToken("nfs.awan@gmail.com", "Apicvalue12345@"));

        // Create an HTTP entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestXmlChanged, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Return response body
        return response.getBody();
    }
}
