package org.parkz.shared.enums;

public enum CurrencyCode {

    VND,
    USD;

    public static CurrencyCode getDefault() {
        return USD;
    }
}
