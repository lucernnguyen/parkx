package org.parkz.modules.user.factory;

import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

public interface IUserFactory extends IDataFactory<String, UserInfo, UserDetails> {

    UserDetails userDetails() throws InvalidException;

}
