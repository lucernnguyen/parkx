package org.parkz.modules.user.mapper;

import org.mapstruct.*;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.factory.impl.PermissionFactory;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.rest.common.factory.data.IPersistDataFactory;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@DecoratedWith(PermissionFactory.class)
public interface PermissionMapper extends IPersistDataFactory<PermissionInfo, PermissionInfo, PermissionEntity> {

    @Named("fromPermissionIdStringListToEntityListMapper")
    default List<PermissionEntity> fromPermissionIdStringListToEntityList(List<String> permissionIds) {
        return new ArrayList<>();
    }

    @Named("fromPermissionEntityListToIdStringListMapper")
    default List<String> fromPermissionEntityListToIdStringList(List<PermissionEntity> permissions) {
        if (permissions != null && !permissions.isEmpty()) {
            return permissions.stream()
                    .map(PermissionEntity::getId)
                    .toList();
        }
        return new ArrayList<>();
    }
}
