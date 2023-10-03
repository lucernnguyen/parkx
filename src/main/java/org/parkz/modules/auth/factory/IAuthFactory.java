package org.parkz.modules.auth.factory;

import org.parkz.modules.auth.model.request.RegisterUserRequest;
import org.parkz.modules.user.model.UserDetails;
import org.springframework.fastboot.exception.InvalidException;

public interface IAuthFactory {

    UserDetails register(RegisterUserRequest request) throws InvalidException;
}
