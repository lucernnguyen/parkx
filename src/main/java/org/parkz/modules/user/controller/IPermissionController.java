package org.parkz.modules.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.logging.LogType;
import org.springframework.fastboot.logging.Logging;
import org.springframework.fastboot.rest.common.controller.IGetInfoListController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Permission Controller")
@RequestMapping("/api/v1/permissions")
@Logging(request = LogType.CALL_API)
public interface IPermissionController
        extends IGetInfoListController<String, PermissionInfo> {

}
