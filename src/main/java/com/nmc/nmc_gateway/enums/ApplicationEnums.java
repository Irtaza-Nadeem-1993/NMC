package com.nmc.nmc_gateway.enums;

public enum ApplicationEnums {
    GOOGLE("google-api-key"),
    AWS("aws-api-key"),
    STRIPE("stripe-api-key");

    private final String apiKey;

    ApplicationEnums(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
