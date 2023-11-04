package org.parkz.modules.wallet.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.wallet.model.TransactionDetails;
import org.parkz.modules.wallet.model.TransactionInfo;
import org.parkz.modules.wallet.model.filter.SystemTransactionFilter;
import org.springframework.fastboot.rest.common.controller.IGetDetailByIdController;
import org.springframework.fastboot.rest.common.controller.IGetInfoPageController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "System Transaction Controller")
@RequestMapping("/api/v1/system/transactions")
public interface ISystemTransactionController extends
        IGetInfoPageController<UUID, TransactionInfo, SystemTransactionFilter>,
        IGetDetailByIdController<UUID, TransactionDetails> {
}
