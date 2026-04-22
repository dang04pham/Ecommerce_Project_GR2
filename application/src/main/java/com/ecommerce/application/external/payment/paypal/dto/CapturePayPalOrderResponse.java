package com.ecommerce.application.external.payment.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CapturePayPalOrderResponse {
    private String orderId;
    private String status;
    private String captureId;
    private String currency;
    private String amount;
}
