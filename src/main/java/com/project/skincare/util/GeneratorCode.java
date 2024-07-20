package com.project.skincare.util;

import org.springframework.stereotype.Component;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Component
public class GeneratorCode {

    private static final String DELIMITER = "-";
    private static final String PREFIX_RECEIPT = "RECEIPT";

    public static String generateCategoryCode(String prefix) {
        return format("%s%s%s", prefix, DELIMITER, randomAlphanumeric(5).toUpperCase());
    }

    public static String generateReceiptCode() {
        return format("%s%s%s", PREFIX_RECEIPT, DELIMITER, randomAlphanumeric(5).toUpperCase());
    }
}
