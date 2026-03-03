package calculator.operations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ScientificOperations {

    public static BigDecimal squareRoot(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("Cannot find square root of negative number.");
        }
        return num.sqrt(new MathContext(10, RoundingMode.HALF_UP));
    }

    public static BigDecimal power(BigDecimal base, int exponent) {
        return base.pow(exponent);
    }

    // Trigonometric functions (input in degrees)
    public static BigDecimal sine(BigDecimal degrees) {
        double radians = Math.toRadians(degrees.doubleValue());
        double result = Math.sin(radians);
        return new BigDecimal(result);
    }

    public static BigDecimal cosine(BigDecimal degrees) {
        double radians = Math.toRadians(degrees.doubleValue());
        double result = Math.cos(radians);
        return new BigDecimal(result);
    }

    public static BigDecimal tangent(BigDecimal degrees) {
        double radians = Math.toRadians(degrees.doubleValue());
        double result = Math.tan(radians);
        return new BigDecimal(result);
    }

    // Additional common scientific operations
    public static BigDecimal logarithm(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Logarithm is undefined for non-positive numbers.");
        }
        double result = Math.log10(num.doubleValue());
        return new BigDecimal(result);
    }

    public static BigDecimal naturalLog(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Natural logarithm is undefined for non-positive numbers.");
        }
        double result = Math.log(num.doubleValue());
        return new BigDecimal(result);
    }

    public static BigDecimal exponential(BigDecimal num) {
        double result = Math.exp(num.doubleValue());
        return new BigDecimal(result);
    }

    public static BigDecimal absolute(BigDecimal num) {
        return num.abs();
    }

    public static BigDecimal factorial(int num) {
        if (num < 0) {
            throw new ArithmeticException("Factorial is undefined for negative numbers.");
        }
        if (num == 0 || num == 1) {
            return BigDecimal.ONE;
        }
        BigDecimal result = BigDecimal.ONE;
        for (int i = 2; i <= num; i++) {
            result = result.multiply(new BigDecimal(i));
        }
        return result;
    }
}
