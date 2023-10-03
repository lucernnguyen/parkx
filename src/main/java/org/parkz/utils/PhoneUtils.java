package org.parkz.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PhoneUtils {

    public static String convertPhoneNumberWithIdentifier(String vnPhone) {
        return vnPhone.replaceFirst("^0", "+84");
    }

    public static String removePhoneNumberIdentifier(String phone) {
        return phone.replaceFirst("^\\+84", "0");
    }
}
