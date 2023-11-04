package org.parkz.modules.wallet.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.controller.ISystemTransactionController;
import org.parkz.modules.wallet.factory.system.ISystemTransactionFactory;
import org.parkz.modules.wallet.model.TransactionDetails;
import org.parkz.modules.wallet.model.TransactionInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SystemTransactionController
        extends BaseController<UUID, TransactionInfo, TransactionDetails>
        implements ISystemTransactionController {

    private final ISystemTransactionFactory systemTransactionFactory;

    @Override
    protected IDataFactory<UUID, TransactionInfo, TransactionDetails> getDataFactory() {
        return systemTransactionFactory;
    }
}
