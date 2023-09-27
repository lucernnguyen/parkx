package org.parkz.modules.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.request.RegisterUserRequest;
import org.springframework.fastboot.rest.common.controller.transactional.ICreateModelTransactionalController;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User Controller")
@RequestMapping("/api/v1/users")
public interface IUserController extends
        ICreateModelTransactionalController<UUID, UserDetails> {

    @GetMapping("/details")
    ResponseEntity<BaseResponse<UserDetails>> userDetails();

    @PostMapping("/register")
    ResponseEntity<BaseResponse<UserDetails>> register(@Valid @RequestBody RegisterUserRequest request);
}
