package com.nmc.nmc_gateway.Scheduler;

import com.nmc.nmc_gateway.service.AuthService;
import com.nmc.nmc_gateway.service.dto.Response.LoginResponseDTO;
import java.util.Iterator;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupScheduler {

    private final AuthService authService;

    public TokenCleanupScheduler(AuthService authService) {
        this.authService = authService;
    }

    @Scheduled(fixedRate = 30000) // Runs every 30 seconds
    public void cleanupExpiredTokens() {
        Map<String, LoginResponseDTO> tokenCache = authService.getTokenCache();
        long currentTime = System.currentTimeMillis();

        Iterator<Map.Entry<String, LoginResponseDTO>> iterator = tokenCache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LoginResponseDTO> entry = iterator.next();
            if (entry.getValue().getExpiryTime() <= currentTime) {
                iterator.remove(); // Remove expired tokens
            }
        }
    }
}
