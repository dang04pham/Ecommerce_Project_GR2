package com.ecommerce.application.external.payment.paypal.controller;

import com.ecommerce.application.external.payment.paypal.dto.CapturePayPalOrderResponse;
import com.ecommerce.application.external.payment.paypal.dto.CreatePayPalOrderResponse;
import com.ecommerce.application.external.payment.paypal.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    private final PayPalService payPalService;

    @Autowired
    public PayPalController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    // 🔹 Create order
    @PostMapping("/create")
    public CreatePayPalOrderResponse createOrder() {
        return payPalService.createOrder();
    }

    // 🔹 Capture order
    @PostMapping("/capture")
    public CapturePayPalOrderResponse captureOrder(@RequestParam String orderId) {
        return payPalService.captureOrder(orderId);
    }
}