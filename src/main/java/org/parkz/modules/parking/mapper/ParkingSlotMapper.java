package org.parkz.modules.parking.mapper;

import org.mapstruct.*;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ParkingSlotMapper extends BaseMapper<ParkingSlotInfo, ParkingSlotInfo, ParkingSlotEntity> {

    @Override
    @Mapping(target = "parking", ignore = true)
    ParkingSlotEntity createConvertToEntity(ParkingSlotInfo detail) throws InvalidException;

    @Override
    @Mapping(target = "parking", ignore = true)
    void updateConvertToEntity(@MappingTarget ParkingSlotEntity entity, ParkingSlotInfo detail) throws InvalidException;


    @Override
    @Mapping(target = "parkingId", source = "parking.id")
    ParkingSlotInfo convertToInfo(ParkingSlotEntity entity) throws InvalidException;
}
