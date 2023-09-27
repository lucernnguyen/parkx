package org.parkz.modules.user.factory;

import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.model.request.RegisterUserRequest;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IUserFactory extends IDataFactory<UUID, UserInfo, UserDetails> {

    UserDetails userDetails() throws InvalidException;


    UserDetails register(RegisterUserRequest request) throws InvalidException;
}
