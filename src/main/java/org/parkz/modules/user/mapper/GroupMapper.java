package org.parkz.modules.user.mapper;

import org.mapstruct.*;
import org.parkz.modules.user.entity.GroupEntity;
import org.parkz.modules.user.factory.impl.GroupFactory;
import org.parkz.modules.user.model.GroupDetails;
import org.parkz.modules.user.model.GroupInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.IPersistDataFactory;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@DecoratedWith(GroupFactory.class)
public interface GroupMapper extends IPersistDataFactory<GroupInfo, GroupDetails, GroupEntity> {

    @Override
    @Mapping(target = "groupPermissions", ignore = true)
    GroupEntity createConvertToEntity(GroupDetails detail) throws InvalidException;

    @Override
    @Mapping(target = "permissions", expression = "java(entity.getPermissionIds())")
    GroupDetails convertToDetail(GroupEntity entity) throws InvalidException;

    @Override
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "groupPermissions", ignore = true)
    void updateConvertToEntity(@MappingTarget GroupEntity entity, GroupDetails detail) throws InvalidException;
}
