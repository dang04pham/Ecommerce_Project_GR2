package com.ecommerce.application.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpResponse {
    private String email;
    private String message;
}
