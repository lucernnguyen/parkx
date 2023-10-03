package org.parkz.modules.user.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.user.controller.IAppPermissionController;
import org.parkz.modules.user.factory.IPermissionFactory;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppPermissionController
        extends BaseController<String, PermissionInfo, PermissionInfo>
        implements IAppPermissionController {

    private final IPermissionFactory permissionFactory;

    @Override
    protected IDataFactory<String, PermissionInfo, PermissionInfo> getDataFactory() {
        return permissionFactory;
    }
}
