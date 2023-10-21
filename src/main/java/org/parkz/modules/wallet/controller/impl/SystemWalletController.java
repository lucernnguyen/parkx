package org.parkz.modules.wallet.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.controller.ISystemWalletController;
import org.parkz.modules.wallet.factory.system.ISystemWalletFactory;
import org.parkz.modules.wallet.model.WalletInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SystemWalletController
        extends BaseController<UUID, WalletInfo, WalletInfo>
        implements ISystemWalletController {

    private final ISystemWalletFactory systemWalletFactory;

    @Override
    protected IDataFactory<UUID, WalletInfo, WalletInfo> getDataFactory() {
        return systemWalletFactory;
    }
}
