package org.parkz.modules.user.controller;

import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.parkz.modules.user.model.request.GetGroupInfoFilterRequest;
import org.springframework.fastboot.rest.common.controller.IDeleteModelByIdController;
import org.springframework.fastboot.rest.common.controller.transactional.ICreateModelTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IGetInfoByIdTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IGetInfoPageTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IUpdateModelTransactionalController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping("/api/v1/groups")
public interface IGroupController extends
        ICreateModelTransactionalController<UUID, GroupDetails>,
        IGetInfoPageTransactionalController<UUID, GroupInfo, GetGroupInfoFilterRequest>,
        IGetInfoByIdTransactionalController<UUID, GroupInfo>,
        IUpdateModelTransactionalController<UUID, GroupDetails>,
        IDeleteModelByIdController<UUID> {
}
