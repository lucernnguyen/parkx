package org.parkz.infrastructure.client.paypal;

import org.parkz.infrastructure.client.paypal.request.CreateOrderRequest;
import org.parkz.infrastructure.client.paypal.response.AccessTokenResponse;
import org.parkz.infrastructure.client.paypal.response.CreateOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "paypal",
        configuration = PayPalClientConfig.class
)
public interface PayPalClient {

    @PostMapping(value = "/v1/oauth2/token", produces = MediaType.APPLICATION_JSON_VALUE)
    AccessTokenResponse getAccessToken(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String encodedBasicCredential,
            @RequestParam(value = "grant_type") String grantType
    );

    @PostMapping(value = "/v2/checkout/orders", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateOrderResponse createOrder(
            @RequestBody CreateOrderRequest request
    );

    @PostMapping(value = "/v2/checkout/orders/{id}/capture", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateOrderResponse captureOrder(
            @PathVariable("id") String id
    );

    default AccessTokenResponse getAccessToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String encodedBasicCredential) {
        return getAccessToken(encodedBasicCredential, "client_credentials");
    }
}
