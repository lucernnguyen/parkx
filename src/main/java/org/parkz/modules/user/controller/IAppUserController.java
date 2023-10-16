package org.parkz.modules.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.user.model.UserDetails;
import org.springframework.fastboot.rest.common.controller.transactional.IUpdateModelTransactionalController;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "App User Controller")
@RequestMapping("/api/v1/app/users")
public interface IAppUserController extends
        IUpdateModelTransactionalController<String, UserDetails> {

    @GetMapping("/details")
    ResponseEntity<BaseResponse<UserDetails>> userDetails();
}
