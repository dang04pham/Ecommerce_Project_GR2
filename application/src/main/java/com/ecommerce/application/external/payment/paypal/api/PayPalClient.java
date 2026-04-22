package com.ecommerce.application.external.payment.paypal.api;

import com.ecommerce.application.external.payment.paypal.config.PayPalConfig;
import com.ecommerce.application.external.payment.paypal.dto.AccessTokenResponse;
import com.ecommerce.application.external.payment.paypal.dto.CapturePayPalOrderResponse;
import com.ecommerce.application.external.payment.paypal.dto.CreatePayPalOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
public class PayPalClient {

    private final PayPalConfig payPalConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public PayPalClient(PayPalConfig payPalConfig, RestTemplate restTemplate) {
        this.payPalConfig = payPalConfig;
        this.restTemplate = restTemplate;
    }

    private String getAccessToken(){
        String auth = payPalConfig.getClientId() + ":" + payPalConfig.getClientSecret();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<AccessTokenResponse> response =
                restTemplate.exchange(
                        payPalConfig.getBaseUrl() + "/v1/oauth2/token",
                        HttpMethod.POST, request,
                        AccessTokenResponse.class
                );

//        Future Improvements:
//        Handles exceptions
        assert response.getBody() != null;
        return response.getBody().getAccessToken();
    }

    public CreatePayPalOrderResponse createPayPalOrder(){
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {
            "intent": "CAPTURE",
            "purchase_units": [
                {
                    "amount": {
                    "currency_code": "USD",
                    "value": "10.00"
                    }
                }
            ]
        }
        """;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                payPalConfig.getBaseUrl() + "/v2/checkout/orders",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map responseBody = response.getBody();

        // ✅ Extract orderId
        String orderId = (String) responseBody.get("id");

        // ✅ Extract approval link
        List<Map<String, Object>> links = (List<Map<String, Object>>) responseBody.get("links");

        String approveUrl = null;

        for (Map<String, Object> link : links) {
            if ("approve".equals(link.get("rel"))) {
                approveUrl = (String) link.get("href");
                break;
            }
        }

        // ✅ Return response
        return new CreatePayPalOrderResponse(orderId, approveUrl);

    }

    public CapturePayPalOrderResponse capturePayPalOrder(String orderId) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                payPalConfig.getBaseUrl() + "/v2/checkout/orders/" + orderId + "/capture",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map body = response.getBody();

        String status = (String) body.get("status");

        // Navigate nested structure:
        // purchase_units → payments → captures
        List<Map<String, Object>> purchaseUnits =
                (List<Map<String, Object>>) body.get("purchase_units");

        Map<String, Object> payments =
                (Map<String, Object>) purchaseUnits.getFirst().get("payments");

        List<Map<String, Object>> captures =
                (List<Map<String, Object>>) payments.get("captures");

        Map<String, Object> capture = captures.get(0);

        String captureId = (String) capture.get("id");

        Map<String, Object> amountObj =
                (Map<String, Object>) capture.get("amount");

        String currency = (String) amountObj.get("currency_code");
        String amount = (String) amountObj.get("value");

        return new CapturePayPalOrderResponse(
                orderId,
                status,
                captureId,
                currency,
                amount
        );
    }
}
