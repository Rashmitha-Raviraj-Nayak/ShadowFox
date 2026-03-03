package calculator.service;

import java.math.BigDecimal;
import java.util.List;
import calculator.operations.*;

/**
 * Service layer for calculator operations
 * Encapsulates all business logic and provides a unified interface
 * for performing calculations across different operation types
 */
public class CalculationService {

    /**
     * Performs basic arithmetic operations
     */
    public BigDecimal performBasicOperation(BigDecimal a, BigDecimal b, String operator) {
        return switch (operator.toLowerCase()) {
            case "add", "+" -> BasicOperations.add(a, b);
            case "subtract", "-" -> BasicOperations.subtract(a, b);
            case "multiply", "*" -> BasicOperations.multiply(a, b);
            case "divide", "/" -> BasicOperations.divide(a, b);
            case "modulo", "%" -> BasicOperations.modulo(a, b);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    /**
     * Performs scientific operations
     */
    public BigDecimal performScientificOperation(BigDecimal num, String operation) {
        return switch (operation.toLowerCase()) {
            case "sqrt" -> ScientificOperations.squareRoot(num);
            case "abs", "absolute" -> ScientificOperations.absolute(num);
            case "ln", "naturallog" -> ScientificOperations.naturalLog(num);
            case "log", "log10" -> ScientificOperations.logarithm(num);
            case "exp", "exponential" -> ScientificOperations.exponential(num);
            default -> throw new IllegalArgumentException("Unknown scientific operation: " + operation);
        };
    }

    /**
     * Performs scientific operations with exponent parameter
     */
    public BigDecimal performScientificOperation(BigDecimal base, int exponent, String operation) {
        return switch (operation.toLowerCase()) {
            case "power", "pow" -> ScientificOperations.power(base, exponent);
            case "factorial", "!" -> ScientificOperations.factorial(exponent);
            default -> throw new IllegalArgumentException("Unknown scientific operation: " + operation);
        };
    }

    /**
     * Performs trigonometric operations (input in degrees)
     */
    public BigDecimal performTrigonometricOperation(BigDecimal degrees, String operation) {
        return switch (operation.toLowerCase()) {
            case "sin", "sine" -> ScientificOperations.sine(degrees);
            case "cos", "cosine" -> ScientificOperations.cosine(degrees);
            case "tan", "tangent" -> ScientificOperations.tangent(degrees);
            default -> throw new IllegalArgumentException("Unknown trigonometric operation: " + operation);
        };
    }

    /**
     * Performs statistical operations
     */
    public BigDecimal performStatisticalOperation(List<BigDecimal> numbers, String operation) {
        return switch (operation.toLowerCase()) {
            case "sum" -> StatisticalOperations.sum(numbers);
            case "mean", "average" -> StatisticalOperations.mean(numbers);
            case "median" -> StatisticalOperations.median(numbers);
            case "stddev", "standarddeviation" -> StatisticalOperations.standardDeviation(numbers);
            case "max", "maximum" -> StatisticalOperations.max(numbers);
            case "min", "minimum" -> StatisticalOperations.min(numbers);
            default -> throw new IllegalArgumentException("Unknown statistical operation: " + operation);
        };
    }

    /**
     * Performs temperature conversion
     */
    public BigDecimal performTemperatureConversion(BigDecimal value, String conversionType) {
        return switch (conversionType.toLowerCase()) {
            case "c2f", "celsius_to_fahrenheit" -> 
                calculator.conversion.TemperatureConverter.celsiusToFahrenheit(value);
            case "f2c", "fahrenheit_to_celsius" -> 
                calculator.conversion.TemperatureConverter.fahrenheitToCelsius(value);
            default -> throw new IllegalArgumentException("Unknown conversion type: " + conversionType);
        };
    }

    /**
     * Performs currency conversion
     */
    public BigDecimal performCurrencyConversion(BigDecimal amount, String conversionType) {
        return switch (conversionType.toLowerCase()) {
            case "usd_to_inr" -> calculator.conversion.CurrencyConverter.usdToInr(amount);
            case "inr_to_usd" -> calculator.conversion.CurrencyConverter.inrToUsd(amount);
            default -> throw new IllegalArgumentException("Unknown conversion type: " + conversionType);
        };
    }

    /**
     * Performs length conversion
     */
    public BigDecimal performLengthConversion(BigDecimal value, String conversionType) {
        return switch (conversionType.toLowerCase()) {
            case "m2ft", "meter_to_feet" -> calculator.conversion.LengthConverter.meterToFeet(value);
            case "ft2m", "feet_to_meter" -> calculator.conversion.LengthConverter.feetToMeter(value);
            case "km2mi", "kilometer_to_mile" -> calculator.conversion.LengthConverter.kilometerToMile(value);
            case "mi2km", "mile_to_kilometer" -> calculator.conversion.LengthConverter.mileToKilometer(value);
            case "cm2in", "centimeter_to_inch" -> calculator.conversion.LengthConverter.centimeterToInch(value);
            case "in2cm", "inch_to_centimeter" -> calculator.conversion.LengthConverter.inchToCentimeter(value);
            default -> throw new IllegalArgumentException("Unknown conversion type: " + conversionType);
        };
    }

    /**
     * Validates if input is a valid number
     */
    public boolean isValidNumber(String input) {
        try {
            new BigDecimal(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a list of numbers from a comma-separated string
     */
    public List<BigDecimal> parseNumberList(String input) {
        String[] parts = input.split(",");
        List<BigDecimal> numbers = new java.util.ArrayList<>();
        for (String part : parts) {
            try {
                numbers.add(new BigDecimal(part.trim()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number in list: " + part);
            }
        }
        return numbers;
    }
}