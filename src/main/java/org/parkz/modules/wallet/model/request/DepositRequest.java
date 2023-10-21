package org.parkz.modules.wallet.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DepositRequest {

    @NotNull
    private UUID transactionId;
    private String token;
    @JsonProperty("PayerID")
    private String payerId;
}
