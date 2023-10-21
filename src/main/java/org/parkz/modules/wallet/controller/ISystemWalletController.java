package org.parkz.modules.wallet.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.wallet.model.WalletInfo;
import org.springframework.fastboot.rest.common.controller.IGetInfoPageController;
import org.springframework.fastboot.rest.common.filter.EmptyFilter;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "System Wallet Controller")
@RequestMapping("/api/v1/system/wallet")
public interface ISystemWalletController extends
        IGetInfoPageController<UUID, WalletInfo, EmptyFilter> {
}

