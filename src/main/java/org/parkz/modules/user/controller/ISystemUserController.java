package org.parkz.modules.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.model.request.GetUserInfoFilterRequest;
import org.springframework.fastboot.rest.common.controller.IGetDetailByIdController;
import org.springframework.fastboot.rest.common.controller.IGetInfoPageController;
import org.springframework.fastboot.rest.common.controller.transactional.ICreateModelTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IDeleteModelByIdTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IUpdateModelTransactionalController;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "System User Controller")
@RequestMapping("/api/v1/system/users")
public interface ISystemUserController extends
        ICreateModelTransactionalController<String, UserDetails>,
        IGetInfoPageController<String, UserInfo, GetUserInfoFilterRequest>,
        IGetDetailByIdController<String, UserDetails>,
        IUpdateModelTransactionalController<String, UserDetails>,
        IDeleteModelByIdTransactionalController<String> {

    @GetMapping("/details")
    ResponseEntity<BaseResponse<UserDetails>> userDetails();

    @Operation(security = @SecurityRequirement(name = "x-api-key"))
    @PostMapping("/admin/create")
    ResponseEntity<BaseResponse<UserDetails>> createAdmin(@Valid @RequestBody UserDetails userDetails);
}
