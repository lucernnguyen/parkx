package org.parkz.modules.wallet.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.wallet.controller.IAppWalletController;
import org.parkz.modules.wallet.factory.app.IAppWalletFactory;
import org.parkz.modules.wallet.model.WalletInfo;
import org.parkz.modules.wallet.model.request.DepositRequest;
import org.parkz.modules.wallet.model.request.InquiryRequest;
import org.parkz.modules.wallet.model.response.InquiryResponse;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppWalletController
        extends BaseController<UUID, WalletInfo, WalletInfo>
        implements IAppWalletController {

    private final IAppWalletFactory appWalletFactory;

    @Override
    public ResponseEntity<BaseResponse<InquiryResponse>> inquiry(InquiryRequest request) {
        return wrapResponse(() -> appWalletFactory.inquiry(request));
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> deposit(DepositRequest request) {
        return wrapResponse(() -> appWalletFactory.deposit(request));
    }

    @Override
    protected IDataFactory<UUID, WalletInfo, WalletInfo> getDataFactory() {
        return appWalletFactory;
    }
}
