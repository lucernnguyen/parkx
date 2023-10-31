package org.parkz.modules.wallet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.parkz.modules.wallet.enums.TransactionStatus;
import org.parkz.modules.wallet.enums.TransactionType;
import org.springframework.fastboot.jpa.entity.Audit;
import org.springframework.fastboot.rest.common.model.IBaseData;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
@NoArgsConstructor
public class TransactionInfo extends Audit<String> implements IBaseData<UUID> {

    protected UUID id;
    private String userEmail;
    private BigDecimal balance;
    private BigDecimal amount;
    private TransactionStatus status;
    private TransactionType transactionType;
}
