package org.parkz.modules.user.controller.impl;

import org.parkz.modules.user.controller.IGroupController;
import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GroupController
        extends BaseController<UUID, GroupInfo, GroupDetails>
        implements IGroupController {
}
