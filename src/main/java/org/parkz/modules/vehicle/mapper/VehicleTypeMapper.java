package org.parkz.modules.vehicle.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.vehicle.entity.VehicleTypeEntity;
import org.parkz.modules.vehicle.enums.VehicleTypeErrorCode;
import org.parkz.modules.vehicle.model.VehicleTypeDetails;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.parkz.modules.vehicle.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Setter(onMethod_ = {@Autowired})
public abstract class VehicleTypeMapper implements BaseMapper<VehicleTypeInfo, VehicleTypeDetails, VehicleTypeEntity> {

    private VehicleTypeRepository vehicleTypeRepository;

    @Named("fromVehicleTypeIdToEntityMapper")
    protected VehicleTypeEntity fromVehicleTypeIdToEntity(Integer id) throws InvalidException {
        return vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new InvalidException(VehicleTypeErrorCode.VEHICLE_TYPE_NOT_FOUND));
    }

}
