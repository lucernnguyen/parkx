package org.parkz.modules.wallet.factory.impl;

import lombok.NoArgsConstructor;
import org.parkz.modules.wallet.entity.WalletEntity;
import org.parkz.modules.wallet.enums.WalletErrorCode;
import org.parkz.modules.wallet.factory.IWalletFactory;
import org.parkz.modules.wallet.model.WalletInfo;
import org.parkz.modules.wallet.repository.WalletRepository;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@NoArgsConstructor
public class WalletFactory
        extends BasePersistDataFactory<UUID, WalletInfo, WalletInfo, UUID, WalletEntity, WalletRepository>
        implements IWalletFactory {

    @Override
    public WalletEntity findWalletByUserIdNotNull(String userId) throws InvalidException {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new InvalidException(notFound()));
    }

    @Override
    protected IErrorCode notFound() {
        return WalletErrorCode.WALLET_NOT_FOUND;
    }
}
