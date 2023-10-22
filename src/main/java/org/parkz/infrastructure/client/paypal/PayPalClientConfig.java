package org.parkz.infrastructure.client.paypal;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.parkz.infrastructure.client.paypal.response.AccessTokenResponse;
import org.parkz.shared.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;

import java.time.Instant;

@RequiredArgsConstructor
public class PayPalClientConfig {

    @Lazy
    private final PayPalClient payPalClient;
    @Value("${paypal.client-id}")
    private final String paypalClientId;
    @Value("${paypal.secret-key}")
    private final String paypalSecretKey;
    private AccessTokenResponse accessToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            if (!template.path().contains("/v1/oauth2/token")) {
                template.header(HttpHeaders.AUTHORIZATION, getToken());
            }
        };
    }

    private String getToken() {
        if (accessToken == null || accessToken.getExpiration().isBefore(Instant.now().minusSeconds(30))) {
            accessToken = payPalClient.getAccessToken(
                    PasswordUtils.encodeBasicCredential(paypalClientId, paypalSecretKey)
            );
            accessToken.setExpiration(Instant.now().plusSeconds(accessToken.getExpiresIn()));
        }
        return accessToken.getTokenType() + " " + accessToken.getAccessToken();
    }

}
