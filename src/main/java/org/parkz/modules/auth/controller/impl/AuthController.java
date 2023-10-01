package org.parkz.modules.auth.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.auth.controller.IAuthController;
import org.parkz.modules.auth.factory.IAuthFactory;
import org.parkz.modules.auth.model.request.RegisterUserRequest;
import org.parkz.modules.user.model.UserDetails;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.exception.RestException;
import org.springframework.fastboot.rest.common.factory.response.IResponseFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements IAuthController {

    private final IResponseFactory responseFactory;
    private final IAuthFactory authFactory;

    @Override
    public ResponseEntity<BaseResponse<UserDetails>> register(RegisterUserRequest request) {
        try {
            var result = authFactory.register(request);
            return responseFactory.success(result);
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }
}
