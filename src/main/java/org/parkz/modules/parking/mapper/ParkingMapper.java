package org.parkz.modules.parking.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.parking.entity.ParkingEntity;
import org.parkz.modules.parking.enums.ParkingErrorCode;
import org.parkz.modules.parking.model.ParkingInfo;
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
public abstract class ParkingMapper implements BaseMapper<ParkingInfo, ParkingInfo, ParkingEntity> {

    private ParkingRepository parkingRepository;

    public ParkingEntity fromParkingIdToParkingEntity() throws InvalidException {
        return parkingRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new InvalidException(ParkingErrorCode.PARKING_NOT_INITIALIZE));
    }
}
