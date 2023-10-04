package org.parkz.modules.vehicle.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.entity.VehicleTypeEntity;
import org.parkz.modules.vehicle.enums.VehicleTypeErrorCode;
import org.parkz.modules.vehicle.model.VehicleInfo;
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
public abstract class VehicleMapper implements BaseMapper<VehicleInfo, VehicleInfo, VehicleEntity> {

    private VehicleTypeRepository vehicleTypeRepository;

    @Override
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "vehicleType", source = "vehicleTypeId", qualifiedByName = "fromVehicleTypeIdToEntityMapper")
    public abstract VehicleEntity createConvertToEntity(VehicleInfo detail) throws InvalidException;

    @Override
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "vehicleType", source = "vehicleTypeId", qualifiedByName = "fromVehicleTypeIdToEntityMapper")
    public abstract void updateConvertToEntity(@MappingTarget VehicleEntity entity, VehicleInfo detail) throws InvalidException;

    @Override
    @InheritConfiguration(name = "convertToInfo")
    public abstract VehicleInfo convertToDetail(VehicleEntity entity) throws InvalidException;

    @Override
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "vehicleTypeId", source = "vehicleType.id")
    public abstract VehicleInfo convertToInfo(VehicleEntity entity) throws InvalidException;

    @Named("fromVehicleTypeIdToEntityMapper")
    protected VehicleTypeEntity fromVehicleTypeIdToEntity(Integer id) throws InvalidException {
        return vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new InvalidException(VehicleTypeErrorCode.VEHICLE_TYPE_NOT_FOUND));
    }
}
