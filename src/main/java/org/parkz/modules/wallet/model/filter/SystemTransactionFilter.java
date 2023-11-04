package org.parkz.modules.wallet.model.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.wallet.entity.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.fastboot.rest.common.filter.IFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class SystemTransactionFilter implements Specification<TransactionEntity>, IFilter {

    private String email;
    private LocalDate startDate = LocalDate.now().minusDays(30);
    private LocalDate endDate = LocalDate.now();

    @Override
    public Predicate toPredicate(@NonNull Root<TransactionEntity> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(email)) {
            predicates.add(cb.like(root.get(TransactionEntity.Fields.user).get(UserEntity.Fields.email), "%" + getEmail().toLowerCase() + "%"));
        }

        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), startDate));
        }

        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), endDate.plusDays(1)));
        }

        query.orderBy(cb.desc(root.get("createdDate")));

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
