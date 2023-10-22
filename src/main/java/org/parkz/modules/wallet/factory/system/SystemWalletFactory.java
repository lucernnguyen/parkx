package org.parkz.modules.wallet.factory.system;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.factory.impl.WalletFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemWalletFactory extends WalletFactory implements ISystemWalletFactory {

}
