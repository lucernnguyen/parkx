package org.parkz.modules.user.mapper;

import org.mapstruct.*;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PermissionMapper extends BaseMapper<PermissionInfo, PermissionInfo, PermissionEntity> {

}
