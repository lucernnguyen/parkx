package org.parkz.modules.user.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.user.entity.PermissionEntity;
import org.parkz.modules.user.model.PermissionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Setter(onMethod_ = {@Autowired})
public abstract class PermissionMapper implements BaseMapper<PermissionInfo, PermissionInfo, PermissionEntity> {

}
