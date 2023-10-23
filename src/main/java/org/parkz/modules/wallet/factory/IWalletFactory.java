package org.parkz.modules.wallet.factory;

import org.parkz.modules.wallet.entity.WalletEntity;
import org.parkz.modules.wallet.model.WalletInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IWalletFactory extends IDataFactory<UUID, WalletInfo, WalletInfo> {

    WalletEntity findWalletByUserIdNotNull(String userId) throws InvalidException;
}
