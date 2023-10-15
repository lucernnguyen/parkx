package org.parkz.modules.user.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.user.controller.ISystemPermissionController;
import org.parkz.modules.user.factory.system.ISystemPermissionFactory;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SystemPermissionController
        extends BaseController<String, PermissionInfo, PermissionInfo>
        implements ISystemPermissionController {

    private final ISystemPermissionFactory systemPermissionFactory;

    @Override
    protected IDataFactory<String, PermissionInfo, PermissionInfo> getDataFactory() {
        return systemPermissionFactory;
    }
}
