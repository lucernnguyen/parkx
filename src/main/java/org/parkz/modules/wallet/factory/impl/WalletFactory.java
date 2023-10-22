package org.parkz.modules.wallet.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.entity.WalletEntity;
import org.parkz.modules.wallet.enums.WalletErrorCode;
import org.parkz.modules.wallet.factory.IWalletFactory;
import org.parkz.modules.wallet.model.WalletInfo;
import org.parkz.modules.wallet.repository.WalletRepository;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;

import java.util.UUID;

@RequiredArgsConstructor
public class WalletFactory
        extends BasePersistDataFactory<UUID, WalletInfo, WalletInfo, UUID, WalletEntity, WalletRepository>
        implements IWalletFactory {

    @Override
    protected IErrorCode notFound() {
        return WalletErrorCode.WALLET_NOT_FOUND;
    }
}
