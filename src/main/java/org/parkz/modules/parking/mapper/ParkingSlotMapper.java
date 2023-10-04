package org.parkz.modules.parking.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.parking.entity.ParkingEntity;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking.repository.ParkingRepository;
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
    @Mapping(target = "parking", expression = "java(parkingMapper.fromParkingIdToParkingEntity())")
    public abstract ParkingSlotEntity createConvertToEntity(ParkingSlotInfo detail) throws InvalidException;

}
