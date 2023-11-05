package org.parkz.modules.wallet.model.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.NonNull;
import org.parkz.modules.wallet.entity.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.fastboot.rest.common.filter.IFilter;
import org.springframework.fastboot.security.utils.JwtUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class AppTransactionFilter implements IFilter, Specification<TransactionEntity> {

    @Override
    public Predicate toPredicate(@NonNull Root<TransactionEntity> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get(TransactionEntity.Fields.userId), JwtUtils.getUserIdString()));
        query.orderBy(cb.desc(root.get("createdDate")));

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
