package org.parkz.modules.wallet.factory.system;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.factory.impl.TransactionFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemTransactionFactory extends TransactionFactory implements ISystemTransactionFactory {

}
