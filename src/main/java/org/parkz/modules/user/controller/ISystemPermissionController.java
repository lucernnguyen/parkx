package org.parkz.modules.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.rest.common.controller.IGetInfoListController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "System Permission Controller")
@RequestMapping("/api/v1/system/permissions")
public interface ISystemPermissionController extends
        IGetInfoListController<String, PermissionInfo> {
}
