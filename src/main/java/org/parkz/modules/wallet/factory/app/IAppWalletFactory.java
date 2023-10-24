package org.parkz.modules.wallet.factory.app;

import org.parkz.modules.wallet.factory.IWalletFactory;
import org.parkz.modules.wallet.model.request.DepositRequest;
import org.parkz.modules.wallet.model.request.InquiryRequest;
import org.parkz.modules.wallet.model.response.DepositResponse;
import org.parkz.modules.wallet.model.response.InquiryResponse;
import org.springframework.fastboot.exception.InvalidException;

public interface IAppWalletFactory extends IWalletFactory {

    InquiryResponse inquiry(InquiryRequest request) throws InvalidException;

    DepositResponse deposit(DepositRequest request) throws InvalidException;
}
