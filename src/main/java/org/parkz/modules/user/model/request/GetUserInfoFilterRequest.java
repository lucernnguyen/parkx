package org.parkz.modules.user.model.request;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.parkz.modules.user.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.fastboot.rest.common.filter.IFilter;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetUserInfoFilterRequest implements Specification<UserEntity>, IFilter {

    private String email;
    private String phone;

    @Override
    public Predicate toPredicate(@NonNull Root<UserEntity> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(getEmail())) {
            predicates.add(cb.equal(cb.lower(root.get(UserEntity.Fields.email)), "%" + getEmail().toLowerCase() + "%"));
        }

        if (StringUtils.isNotBlank(getPhone())) {
            predicates.add(cb.equal(cb.lower(root.get(UserEntity.Fields.phone)), "%" + getPhone().toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
