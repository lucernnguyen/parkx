package org.parkz.modules.user.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.user.controller.IAppUserController;
import org.parkz.modules.user.factory.IUserFactory;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppUserController
        extends BaseController<String, UserInfo, UserDetails>
        implements IAppUserController {

    @Qualifier("appUserFactory")
    private final IUserFactory userFactory;

    @Override
    public ResponseEntity<BaseResponse<UserDetails>> userDetails() {
        return wrapResponse(userFactory::userDetails);
    }

    @Override
    protected IDataFactory<String, UserInfo, UserDetails> getDataFactory() {
        return userFactory;
    }
}
