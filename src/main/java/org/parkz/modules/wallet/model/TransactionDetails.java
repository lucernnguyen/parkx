package org.parkz.modules.wallet.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.parkz.infrastructure.client.paypal.response.CreateOrderResponse;

import java.util.UUID;

@Data
@SuperBuilder
public class TransactionDetails extends TransactionInfo {

    private CreateOrderResponse orderData;
    private String refTransactionId;
    private UUID walletId;
    private String userId;
    private String userEmail;
}
