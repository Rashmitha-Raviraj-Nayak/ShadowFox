package calculator.conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LengthConverter {

    /**
     * Converts meters to feet
     * 1 meter = 3.28084 feet
     */
    public static BigDecimal meterToFeet(BigDecimal meters) {
        if (meters == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return meters.multiply(new BigDecimal("3.28084")).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Converts feet to meters
     * 1 foot = 0.3048 meters
     */
    public static BigDecimal feetToMeter(BigDecimal feet) {
        if (feet == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return feet.multiply(new BigDecimal("0.3048")).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Converts kilometers to miles
     * 1 kilometer = 0.621371 miles
     */
    public static BigDecimal kilometerToMile(BigDecimal km) {
        if (km == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return km.multiply(new BigDecimal("0.621371")).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Converts miles to kilometers
     * 1 mile = 1.60934 kilometers
     */
    public static BigDecimal mileToKilometer(BigDecimal mile) {
        if (mile == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return mile.multiply(new BigDecimal("1.60934")).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Converts centimeters to inches
     * 1 centimeter = 0.393701 inches
     */
    public static BigDecimal centimeterToInch(BigDecimal cm) {
        if (cm == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return cm.multiply(new BigDecimal("0.393701")).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Converts inches to centimeters
     * 1 inch = 2.54 centimeters
     */
    public static BigDecimal inchToCentimeter(BigDecimal inch) {
        if (inch == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return inch.multiply(new BigDecimal("2.54")).setScale(4, RoundingMode.HALF_UP);
    }
}
