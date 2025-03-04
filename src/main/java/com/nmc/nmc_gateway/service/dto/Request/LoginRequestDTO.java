package com.nmc.nmc_gateway.service.dto.Request;

import static com.nmc.nmc_gateway.utils.Constants.PASSWORD;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {

    private String grantType = PASSWORD;
    private String username;
    private String password;
}
