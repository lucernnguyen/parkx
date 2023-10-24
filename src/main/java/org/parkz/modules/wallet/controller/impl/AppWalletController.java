package org.parkz.modules.wallet.controller.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.wallet.controller.IAppWalletController;
import org.parkz.modules.wallet.factory.app.IAppWalletFactory;
import org.parkz.modules.wallet.model.WalletInfo;
import org.parkz.modules.wallet.model.request.DepositRequest;
import org.parkz.modules.wallet.model.request.InquiryRequest;
import org.parkz.modules.wallet.model.response.DepositResponse;
import org.parkz.modules.wallet.model.response.InquiryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.exception.RestException;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppWalletController
        extends BaseController<UUID, WalletInfo, WalletInfo>
        implements IAppWalletController {

    private final IAppWalletFactory appWalletFactory;
    @Value("${mobile.app-open-url}")
    private final String mobileAppOpenUrl;

    @Override
    public ResponseEntity<BaseResponse<InquiryResponse>> inquiry(InquiryRequest request) {
        return wrapResponse(() -> appWalletFactory.inquiry(request));
    }

    @Override
    public void deposit(DepositRequest request, HttpServletResponse response) {
        try {
            DepositResponse depositResponse = appWalletFactory.deposit(request);
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(mobileAppOpenUrl)
                    .queryParam("transactionId", depositResponse.getTransactionId())
                    .build();
            log.info("[WALLET] Redirect user to deeplink {}", uriComponents.toUriString());
            response.setHeader(HttpHeaders.LOCATION, uriComponents.toUriString());
            response.setStatus(HttpStatus.SEE_OTHER.value());
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }

    @Override
    protected IDataFactory<UUID, WalletInfo, WalletInfo> getDataFactory() {
        return appWalletFactory;
    }
}
