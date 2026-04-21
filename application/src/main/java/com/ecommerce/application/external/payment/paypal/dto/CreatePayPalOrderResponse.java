package com.ecommerce.application.external.payment.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePayPalOrderResponse {
    private String orderId;
    private String approvalUrl;
}
