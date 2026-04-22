package com.ecommerce.application.external.payment.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccessTokenResponse {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
