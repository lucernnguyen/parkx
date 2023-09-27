package org.parkz.modules.user.controller.impl;

import org.parkz.modules.user.controller.IPermissionController;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController
        extends BaseController<String, PermissionInfo, PermissionInfo>
        implements IPermissionController {
}
