package calculator.conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

    // Fixed demo rate (real apps use APIs)
    private static final BigDecimal USD_TO_INR = new BigDecimal("83.00");

    public static BigDecimal usdToInr(BigDecimal usd) {
        return usd.multiply(USD_TO_INR).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal inrToUsd(BigDecimal inr) {
        return inr.divide(USD_TO_INR, 2, RoundingMode.HALF_UP);
    }
}
