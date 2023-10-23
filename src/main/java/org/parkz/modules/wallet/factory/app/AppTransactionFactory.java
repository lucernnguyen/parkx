package org.parkz.modules.wallet.factory.app;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.factory.impl.TransactionFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppTransactionFactory extends TransactionFactory implements IAppTransactionFactory {
}
