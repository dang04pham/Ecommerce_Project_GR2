package com.ecommerce.application.external.payment.paypal.service;

import com.ecommerce.application.external.payment.paypal.api.PayPalClient;
import com.ecommerce.application.external.payment.paypal.dto.CapturePayPalOrderResponse;
import com.ecommerce.application.external.payment.paypal.dto.CreatePayPalOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayPalService {

    private final PayPalClient payPalClient;

    @Autowired
    public PayPalService(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public CreatePayPalOrderResponse createOrder() {
        return payPalClient.createPayPalOrder();
    }

    public CapturePayPalOrderResponse captureOrder(String orderId) {
        return payPalClient.capturePayPalOrder(orderId);
    }
}
