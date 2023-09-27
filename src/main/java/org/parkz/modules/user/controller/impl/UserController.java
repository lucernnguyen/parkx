package org.parkz.modules.user.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.user.controller.IUserController;
import org.parkz.modules.user.factory.IUserFactory;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.model.request.RegisterUserRequest;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController
        extends BaseController<UUID, UserInfo, UserDetails>
        implements IUserController {

    private final IUserFactory userFactory;

    @Override
    public ResponseEntity<BaseResponse<UserDetails>> userDetails() {
        return wrapResponse(userFactory::userDetails);
    }

    @Override
    public ResponseEntity<BaseResponse<UserDetails>> register(RegisterUserRequest request) {
        return wrapResponse(() -> userFactory.register(request));
    }
}
