package org.parkz.modules.user.factory.impl;

import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.enums.UserErrorCode;
import org.parkz.modules.user.factory.IUserFactory;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class UserFactory
        extends BasePersistDataFactory<String, UserInfo, UserDetails, String, UserEntity, UserRepository>
        implements IUserFactory {

    @Override
    public UserDetails userDetails() throws InvalidException {
        UserEntity user = repository.findById(JwtUtils.getUserIdString())
                .orElseThrow(() -> new InvalidException(notFound()));
        return convertToDetail(user);
    }

    @Override
    protected IErrorCode notFound() {
        return UserErrorCode.USER_NOT_FOUND;
    }

}
