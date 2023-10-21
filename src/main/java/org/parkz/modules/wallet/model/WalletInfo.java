package org.parkz.modules.wallet.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class WalletInfo implements IBaseData<UUID> {

    private UUID id;
    private BigDecimal balance;
    private BigDecimal debt;
    private String userId;
}
