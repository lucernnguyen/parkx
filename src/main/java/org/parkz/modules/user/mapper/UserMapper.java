package org.parkz.modules.user.mapper;

import com.google.firebase.auth.UserRecord;
import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.auth.model.request.RegisterUserRequest;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.utils.PhoneUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {PhoneUtils.class}
)
@Setter(onMethod_ = {@Autowired})
public abstract class UserMapper implements BaseMapper<UserInfo, UserDetails, UserEntity> {

    @Override
    @Mapping(target = "groupId", source = "group.id")
    public abstract UserInfo convertToInfo(UserEntity entity) throws InvalidException;

    @Override
    @Mapping(target = "groupId", source = "group.id")
    public abstract UserDetails convertToDetail(UserEntity entity) throws InvalidException;

    @Mapping(target = "id", source = "uid")
    @Mapping(target = "phone", expression = "java(PhoneUtils.removePhoneNumberIdentifier(userRecord.getPhoneNumber()))")
    @Mapping(target = "name", source = "displayName")
    @Mapping(target = "avatar", source = "photoUrl")
    @Mapping(target = "active", expression = "java(!userRecord.isDisabled())")
    public abstract UserEntity fromUserRecordToEntity(UserRecord userRecord);

    public abstract void updateInfoRegisterToUserEntity(@MappingTarget UserEntity userEntity, RegisterUserRequest request);
}
