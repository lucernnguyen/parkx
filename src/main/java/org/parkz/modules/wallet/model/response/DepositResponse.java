package org.parkz.modules.wallet.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class DepositResponse {

    private UUID transactionId;
}
