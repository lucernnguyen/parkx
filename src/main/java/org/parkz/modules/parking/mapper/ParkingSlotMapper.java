package org.parkz.modules.parking.mapper;

import org.mapstruct.*;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ParkingSlotMapper extends BaseMapper<ParkingSlotInfo, ParkingSlotInfo, ParkingSlotEntity> {

}
