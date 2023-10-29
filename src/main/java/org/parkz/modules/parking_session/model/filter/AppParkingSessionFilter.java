package org.parkz.modules.parking_session.model.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.NonNull;
import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.fastboot.rest.common.filter.IFilter;
import org.springframework.fastboot.security.utils.JwtUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class AppParkingSessionFilter implements Specification<ParkingSessionEntity>, IFilter {

    private UUID vehicleId;
    private Boolean active;

    @Override
    public Predicate toPredicate(@NonNull Root<ParkingSessionEntity> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (getVehicleId() != null) {
            predicates.add(cb.equal(root.get(ParkingSessionEntity.Fields.vehicleId), getVehicleId()));
        }

        predicates.add(cb.isNotNull(root.get(ParkingSessionEntity.Fields.checkInTime)));
        if (getActive() != null) {
            predicates.add(cb.isNull(root.get(ParkingSessionEntity.Fields.checkOutTime)));
        } else {
            predicates.add(cb.isNotNull(root.get(ParkingSessionEntity.Fields.checkOutTime)));
        }

        predicates.add(cb.equal(root.get("vehicle").get("userId"), JwtUtils.getUserIdString()));
        query.orderBy(cb.desc(root.get(ParkingSessionEntity.Fields.createdDate)));

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
