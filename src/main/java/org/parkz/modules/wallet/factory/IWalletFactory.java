package org.parkz.modules.wallet.factory;

import org.parkz.modules.wallet.model.WalletInfo;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IWalletFactory extends IDataFactory<UUID, WalletInfo, WalletInfo> {
}
