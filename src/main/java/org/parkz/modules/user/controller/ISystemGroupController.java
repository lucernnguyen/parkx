package org.parkz.modules.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.parkz.modules.user.model.request.GetGroupInfoFilterRequest;
import org.springframework.fastboot.rest.common.controller.transactional.*;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "System Group Controller")
@RequestMapping("/api/v1/system/groups")
public interface ISystemGroupController extends
        ICreateModelTransactionalController<UUID, GroupDetails>,
        IGetInfoPageTransactionalController<UUID, GroupInfo, GetGroupInfoFilterRequest>,
        IGetInfoByIdTransactionalController<UUID, GroupInfo>,
        IUpdateModelTransactionalController<UUID, GroupDetails>,
        IDeleteModelByIdTransactionalController<UUID> {

    @PatchMapping("/{id}/default")
    ResponseEntity<BaseResponse<SuccessResponse>> changeDefaultGroup(@PathVariable("id") UUID id);
}
