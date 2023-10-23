package org.parkz.modules.wallet.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.controller.IAppTransactionController;
import org.parkz.modules.wallet.factory.app.IAppTransactionFactory;
import org.parkz.modules.wallet.model.TransactionDetails;
import org.parkz.modules.wallet.model.TransactionInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppTransactionController
        extends BaseController<UUID, TransactionInfo, TransactionDetails>
        implements IAppTransactionController {

    private final IAppTransactionFactory appTransactionFactory;

    @Override
    protected IDataFactory<UUID, TransactionInfo, TransactionDetails> getDataFactory() {
        return appTransactionFactory;
    }
}
