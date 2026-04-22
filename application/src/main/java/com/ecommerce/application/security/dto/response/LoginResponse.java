package com.ecommerce.application.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String jwtToken;
    private String tokenType;
    private String username;
}
