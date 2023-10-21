package org.parkz.cache.factory;

import lombok.RequiredArgsConstructor;
import org.parkz.cache.domain.Currency;
import org.parkz.cache.repository.CurrencyRepository;
import org.parkz.infrastructure.client.currency.CurrencyClient;
import org.parkz.shared.enums.CurrencyCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyFactory {

    private final CurrencyClient currencyClient;
    private final CurrencyRepository currencyRepository;

    public Currency getByCode(CurrencyCode currencyCode) {
        return currencyRepository.findById(currencyCode)
                .orElseGet(() -> {
                    Currency response = currencyClient.getCurrencyRates(currencyCode);
                    response.setTtl(response.getTimeNextUpdateUnix() - System.currentTimeMillis() - 30);
                    return currencyRepository.save(response);
                });
    }
}
