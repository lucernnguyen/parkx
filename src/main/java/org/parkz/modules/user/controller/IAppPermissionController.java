package org.parkz.modules.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.rest.common.controller.IGetInfoListController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "App Permission Controller")
@RequestMapping("/api/v1/app/permissions")
public interface IAppPermissionController
        extends IGetInfoListController<String, PermissionInfo> {

}
