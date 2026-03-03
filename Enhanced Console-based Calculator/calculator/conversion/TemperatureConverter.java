package calculator.conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TemperatureConverter {

    public static BigDecimal celsiusToFahrenheit(BigDecimal c) {
        return c.multiply(BigDecimal.valueOf(9))
                .divide(BigDecimal.valueOf(5), 5, RoundingMode.HALF_UP)
                .add(BigDecimal.valueOf(32));
    }

    public static BigDecimal fahrenheitToCelsius(BigDecimal f) {
        return f.subtract(BigDecimal.valueOf(32))
                .multiply(BigDecimal.valueOf(5))
                .divide(BigDecimal.valueOf(9), 5, RoundingMode.HALF_UP);
    }
}
