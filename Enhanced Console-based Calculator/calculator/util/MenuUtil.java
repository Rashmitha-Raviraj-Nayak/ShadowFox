package calculator.util;

public class MenuUtil {

    public static void showMainMenu() {
        System.out.println("\n========= ENHANCED CALCULATOR =========");
        System.out.println("1. Basic Calculator");
        System.out.println("2. Scientific Calculator");
        System.out.println("3. Temperature Conversion");
        System.out.println("4. Currency Conversion");
        System.out.println("5. Exit");
    }

    public static void showBasicMenu() {
        System.out.println("\n--- Basic Operations ---");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
    }

    public static void showScientificMenu() {
        System.out.println("\n--- Scientific Operations ---");
        System.out.println("1. Square Root");
        System.out.println("2. Power");
    }

    public static void showTemperatureMenu() {
        System.out.println("\n--- Temperature Conversion ---");
        System.out.println("1. Celsius → Fahrenheit");
        System.out.println("2. Fahrenheit → Celsius");
    }

    public static void showCurrencyMenu() {
        System.out.println("\n--- Currency Conversion ---");
        System.out.println("1. USD → INR");
        System.out.println("2. INR → USD");
    }
}
