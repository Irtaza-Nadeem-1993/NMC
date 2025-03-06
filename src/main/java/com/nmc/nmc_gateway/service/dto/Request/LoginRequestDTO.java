package com.nmc.nmc_gateway.service.dto.Request;

import static com.nmc.nmc_gateway.utils.Constants.PASSWORD;

public class LoginRequestDTO {

    private String grantType = PASSWORD;
    private String username;
    private String password;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String grantType, String username, String password) {
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
