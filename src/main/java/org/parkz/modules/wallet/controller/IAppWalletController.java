package org.parkz.modules.wallet.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.parkz.modules.wallet.model.WalletInfo;
import org.parkz.modules.wallet.model.request.DepositRequest;
import org.parkz.modules.wallet.model.request.InquiryRequest;
import org.parkz.modules.wallet.model.response.InquiryResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.fastboot.rest.common.controller.IGetDetailByContextController;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "App Wallet Controller")
@RequestMapping("/api/v1/app/wallet")
public interface IAppWalletController extends IGetDetailByContextController<UUID, WalletInfo> {

    @PostMapping("/inquiry")
    ResponseEntity<BaseResponse<InquiryResponse>> inquiry(@Valid @RequestBody InquiryRequest request);

    @GetMapping("/public/deposit")
    void deposit(@Valid @ParameterObject DepositRequest request, HttpServletResponse response);
}
