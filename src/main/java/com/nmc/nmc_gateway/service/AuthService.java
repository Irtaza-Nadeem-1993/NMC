package com.nmc.nmc_gateway.service;

import static com.nmc.nmc_gateway.utils.Constants.*;

import com.nmc.nmc_gateway.service.dto.Response.LoginResponseDTO;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Value("${tatmeen.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public static final Map<String, LoginResponseDTO> tokenCache = new HashMap<>();

    public Map<String, LoginResponseDTO> getTokenCache() {
        return tokenCache;
    }

    public String login(String username, String password) {
        if (isTokenValid(username)) {
            return tokenCache.get(username).getAccessToken(); // Return cached token if valid
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(API_KEY, apiKey);

        String requestBody =
            "grant_type=" +
            URLEncoder.encode("password", StandardCharsets.UTF_8) +
            "&username=" +
            URLEncoder.encode(username, StandardCharsets.UTF_8) +
            "&password=" +
            URLEncoder.encode(password, StandardCharsets.UTF_8);

        String authUrl = TATMEEN_BASE_URL + TATMEEN_LOGIN_URL;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<LoginResponseDTO> response = restTemplate.exchange(authUrl, HttpMethod.POST, request, LoginResponseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            LoginResponseDTO loginResponse = response.getBody();
            long expiryTimestamp = System.currentTimeMillis() + (loginResponse.getExpiresIn() * 1000L);
            loginResponse.setExpiryTime(expiryTimestamp);

            tokenCache.put(username, loginResponse);
            return loginResponse.getAccessToken();
        } else {
            throw new RuntimeException("Failed to login to Tatmeen API: " + response.getStatusCode());
        }
    }

    private boolean isTokenValid(String username) {
        LoginResponseDTO tokenData = tokenCache.get(username);
        if (tokenData == null) return false;

        long currentTime = System.currentTimeMillis();
        return tokenData.getExpiryTime() > currentTime;
    }

    public String getAccessToken(String username, String password) {
        return login(username, password);
    }
}
