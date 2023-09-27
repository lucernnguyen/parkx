package org.parkz.modules.user.mapper;

import com.google.firebase.auth.UserRecord;
import org.mapstruct.*;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.factory.impl.UserFactory;
import org.parkz.modules.user.model.UserDetails;
import org.parkz.modules.user.model.UserInfo;
import org.parkz.modules.user.model.request.RegisterUserRequest;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.IPersistDataFactory;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@DecoratedWith(UserFactory.class)
public interface UserMapper extends IPersistDataFactory<UserInfo, UserDetails, UserEntity> {

    @Override
    @Mapping(target = "groupId", source = "group.id")
    UserInfo convertToInfo(UserEntity entity) throws InvalidException;

    @Override
    UserDetails convertToDetail(UserEntity entity) throws InvalidException;

    @Mapping(target = "subjectId", source = "uid")
    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "name", source = "displayName")
    @Mapping(target = "avatar", source = "photoUrl")
    @Mapping(target = "active", expression = "java(!userRecord.isDisabled())")
    UserEntity fromUserRecordToEntity(UserRecord userRecord);

    void updateInfoRegisterToUserEntity(@MappingTarget UserEntity userEntity, RegisterUserRequest request);
}
