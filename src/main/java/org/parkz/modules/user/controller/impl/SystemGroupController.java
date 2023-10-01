package org.parkz.modules.user.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.user.controller.ISystemGroupController;
import org.parkz.modules.user.factory.IGroupFactory;
import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SystemGroupController
        extends BaseController<UUID, GroupInfo, GroupDetails>
        implements ISystemGroupController {

    private final IGroupFactory groupFactory;

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> changeDefaultGroup(UUID id) {
        return wrapResponse(() -> groupFactory.changeDefault(id));
    }

    @Override
    protected IDataFactory<UUID, GroupInfo, GroupDetails> getDataFactory() {
        return groupFactory;
    }
}
