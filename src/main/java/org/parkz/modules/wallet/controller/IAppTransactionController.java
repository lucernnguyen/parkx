package org.parkz.modules.wallet.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.wallet.model.TransactionDetails;
import org.parkz.modules.wallet.model.TransactionInfo;
import org.springframework.fastboot.rest.common.controller.IGetDetailByIdController;
import org.springframework.fastboot.rest.common.controller.IGetInfoPageController;
import org.springframework.fastboot.rest.common.filter.EmptyFilter;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "App Transaction Controller")
@RequestMapping("/api/v1/app/transactions")
public interface IAppTransactionController extends
        IGetInfoPageController<UUID, TransactionInfo, EmptyFilter>,
        IGetDetailByIdController<UUID, TransactionDetails> {
}
