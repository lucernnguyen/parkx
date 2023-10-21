package org.parkz.cache.repository;

import org.parkz.cache.domain.Currency;
import org.parkz.shared.enums.CurrencyCode;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends KeyValueRepository<Currency, CurrencyCode> {
}
