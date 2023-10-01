package org.parkz.modules.user.factory.system;

import org.parkz.modules.user.factory.IUserFactory;
import org.parkz.modules.user.model.UserDetails;
import org.springframework.fastboot.exception.InvalidException;

public interface ISystemUserFactory extends IUserFactory {

    UserDetails createAdmin(UserDetails userDetails) throws InvalidException;
}
