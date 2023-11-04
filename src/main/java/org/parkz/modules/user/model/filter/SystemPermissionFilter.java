package org.parkz.modules.user.model.filter;

import jakarta.persistence.criteria.*;
import lombok.Data;
import lombok.NonNull;
import org.parkz.modules.user.entity.GroupPermissionEntity;
import org.parkz.modules.user.entity.PermissionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.fastboot.rest.common.filter.IFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class SystemPermissionFilter implements Specification<PermissionEntity>, IFilter {

    private UUID groupId;

    @Override
    public Predicate toPredicate(@NonNull Root<PermissionEntity> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (getGroupId() != null) {
            Join<PermissionEntity, GroupPermissionEntity> gpJoin = root.join(PermissionEntity.Fields.groups);
            predicates.add(cb.equal(gpJoin.get("groupId"), getGroupId()));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
