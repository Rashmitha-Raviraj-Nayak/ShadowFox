package calculator;

import calculator.operations.BasicOperations;
import calculator.operations.ScientificOperations;
import calculator.conversion.TemperatureConverter;
import calculator.conversion.CurrencyConverter;
import calculator.util.InputUtil;
import calculator.util.MenuUtil;

import java.math.BigDecimal;

public class Calculator {

    public void start() {
        boolean running = true;

        while (running) {
            MenuUtil.showMainMenu();
            int choice = InputUtil.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> basicCalculator();
                case 2 -> scientificCalculator();
                case 3 -> temperatureConversion();
                case 4 -> currencyConversion();
                case 5 -> {
                    System.out.println("Thank you for using Enhanced Calculator");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // BASIC
    private void basicCalculator() {
        BigDecimal a = InputUtil.readBigDecimal("Enter first number: ");
        BigDecimal b = InputUtil.readBigDecimal("Enter second number: ");

        MenuUtil.showBasicMenu();
        int op = InputUtil.readInt("Choose operation: ");

        try {
            BigDecimal result = switch (op) {
                case 1 -> BasicOperations.add(a, b);
                case 2 -> BasicOperations.subtract(a, b);
                case 3 -> BasicOperations.multiply(a, b);
                case 4 -> BasicOperations.divide(a, b);
                default -> throw new IllegalArgumentException("Invalid operation selected.");
            };
            System.out.println("Result = " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // SCIENTIFIC
    private void scientificCalculator() {
        BigDecimal num = InputUtil.readBigDecimal("Enter number: ");

        MenuUtil.showScientificMenu();
        int op = InputUtil.readInt("Choose operation: ");

        try {
            BigDecimal result = switch (op) {
                case 1 -> ScientificOperations.squareRoot(num);
                case 2 -> {
                    int power = InputUtil.readInt("Enter power: ");
                    yield ScientificOperations.power(num, power);
                }
                default -> throw new IllegalArgumentException("Invalid scientific operation.");
            };
            System.out.println("Result = " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // TEMPERATURE
    private void temperatureConversion() {
        MenuUtil.showTemperatureMenu();
        int op = InputUtil.readInt("Choose conversion: ");

        BigDecimal temp = InputUtil.readBigDecimal("Enter temperature: ");

        try {
            BigDecimal result = switch (op) {
                case 1 -> TemperatureConverter.celsiusToFahrenheit(temp);
                case 2 -> TemperatureConverter.fahrenheitToCelsius(temp);
                default -> throw new IllegalArgumentException("Invalid temperature conversion.");
            };
            System.out.println("Converted Value = " + result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // CURRENCY
    private void currencyConversion() {
        System.out.println("Fixed rate demo: 1 USD = 83 INR");

        MenuUtil.showCurrencyMenu();
        int op = InputUtil.readInt("Choose conversion: ");

        BigDecimal amount = InputUtil.readBigDecimal("Enter amount: ");

        try {
            BigDecimal result = switch (op) {
                case 1 -> CurrencyConverter.usdToInr(amount);
                case 2 -> CurrencyConverter.inrToUsd(amount);
                default -> throw new IllegalArgumentException("Invalid currency conversion.");
            };
            System.out.println("Converted Amount = " + result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
