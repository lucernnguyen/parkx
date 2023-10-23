package org.parkz.modules.wallet.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum TransactionErrorCode implements IErrorCode {

    TRANSACTION_NOT_FOUND(1, StatusMapping.NOT_FOUND),
    TRANSACTION_BALANCE_NOT_ENOUGH(2, StatusMapping.BAD_REQUEST)
    ;
    private final int code;
    private final StatusMapping statusMapping;
}
