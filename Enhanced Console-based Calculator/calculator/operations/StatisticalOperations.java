package calculator.operations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StatisticalOperations {

    /**
     * Calculates the mean (average) of a list of numbers
     */
    public static BigDecimal mean(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new ArithmeticException("Cannot calculate mean of empty list");
        }
        BigDecimal sum = numbers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(numbers.size()), 10, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the median of a list of numbers
     */
    public static BigDecimal median(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new ArithmeticException("Cannot calculate median of empty list");
        }
        List<BigDecimal> sorted = numbers.stream().sorted().toList();
        int size = sorted.size();
        if (size % 2 == 0) {
            return sorted.get(size / 2 - 1).add(sorted.get(size / 2))
                    .divide(new BigDecimal(2), 10, RoundingMode.HALF_UP);
        }
        return sorted.get(size / 2);
    }

    /**
     * Calculates the standard deviation of a list of numbers
     */
    public static BigDecimal standardDeviation(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new ArithmeticException("Cannot calculate standard deviation of empty list");
        }
        if (numbers.size() < 2) {
            throw new ArithmeticException("Need at least 2 numbers for standard deviation");
        }
        
        BigDecimal mean = mean(numbers);
        BigDecimal sumSquaredDiffs = numbers.stream()
                .map(n -> n.subtract(mean).pow(2))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal variance = sumSquaredDiffs.divide(new BigDecimal(numbers.size()), 10, RoundingMode.HALF_UP);
        return variance.sqrt(new java.math.MathContext(10, RoundingMode.HALF_UP));
    }

    /**
     * Calculates the sum of a list of numbers
     */
    public static BigDecimal sum(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return numbers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Finds the maximum value in a list of numbers
     */
    public static BigDecimal max(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new ArithmeticException("Cannot find max of empty list");
        }
        return numbers.stream().max(BigDecimal::compareTo).get();
    }

    /**
     * Finds the minimum value in a list of numbers
     */
    public static BigDecimal min(List<BigDecimal> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new ArithmeticException("Cannot find min of empty list");
        }
        return numbers.stream().min(BigDecimal::compareTo).get();
    }

    /**
     * Calculates the count (number of items) in a list
     */
    public static int count(List<BigDecimal> numbers) {
        if (numbers == null) {
            return 0;
        }
        return numbers.size();
    }
}
