package org.parkz.modules.parking.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Setter(onMethod_ = {@Autowired})
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class ParkingSlotMapper implements BaseMapper<ParkingSlotInfo, ParkingSlotInfo, ParkingSlotEntity> {

    protected ParkingMapper parkingMapper;

    @Override
    @Mapping(target = "parkingId", expression = "java(parkingMapper.fromParkingIdToParkingEntity().getId())")
    public abstract ParkingSlotEntity createConvertToEntity(ParkingSlotInfo detail) throws InvalidException;

}
