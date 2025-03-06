package com.nmc.nmc_gateway.Scheduler;

import com.nmc.nmc_gateway.service.AuthService;
import com.nmc.nmc_gateway.service.dto.Response.LoginResponseDTO;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupScheduler {

    @Scheduled(fixedRate = 30000) // Runs every 30 seconds
    public void cleanupExpiredTokens() {
        Map<String, LoginResponseDTO> tokenCache = AuthService.tokenCache;
        long currentTime = System.currentTimeMillis();

        // Remove expired tokens
        tokenCache.entrySet().removeIf(entry -> entry.getValue().getExpiryTime() <= currentTime);
    }
}
