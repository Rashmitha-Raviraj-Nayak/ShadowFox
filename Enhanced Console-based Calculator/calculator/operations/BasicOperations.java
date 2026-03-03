package calculator.operations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BasicOperations {

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a.divide(b, 10, RoundingMode.HALF_UP); // precision-safe
    }

    public static BigDecimal percentage(BigDecimal num) {
        return num.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    }

    public static BigDecimal squareRoot(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("Cannot find square root of negative number.");
        }
        return num.sqrt(new java.math.MathContext(10, RoundingMode.HALF_UP));
    }

    public static BigDecimal square(BigDecimal num) {
        return num.multiply(num);
    }

    public static BigDecimal power(BigDecimal base, int exponent) {
        return base.pow(exponent);
    }

    public static BigDecimal modulo(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Modulo by zero is not allowed.");
        }
        return a.remainder(b);
    }

    public static BigDecimal inverse(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Inverse of zero is not allowed.");
        }
        return BigDecimal.ONE.divide(num, 10, RoundingMode.HALF_UP);
    }

    public static BigDecimal toggleSign(BigDecimal num) {
        return num.negate();
    }
}
