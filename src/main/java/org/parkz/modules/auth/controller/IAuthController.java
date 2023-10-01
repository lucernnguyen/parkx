package org.parkz.modules.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.parkz.modules.auth.model.request.RegisterUserRequest;
import org.parkz.modules.user.model.UserDetails;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth Controller")
@RequestMapping("/api/v1/auth")
public interface IAuthController {

    @PostMapping("/register")
    ResponseEntity<BaseResponse<UserDetails>> register(@Valid @RequestBody RegisterUserRequest request);
}
