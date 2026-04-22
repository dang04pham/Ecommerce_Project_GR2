package com.ecommerce.application.security.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
}
