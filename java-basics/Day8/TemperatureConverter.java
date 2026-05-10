import java.util.Scanner;

public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Temperature Converter");
        System.out.println("1) Celsius to Fahrenheit");
        System.out.println("2) Fahrenheit to Celsius");
        System.out.println("3) Celsius to Kelvin");
        System.out.println("4) Kelvin to Celsius");
        System.out.print("Choose conversion (1-4): ");
        String choice = scanner.nextLine();
        System.out.print("Enter the temperature value: ");
        try {
            double value = Double.parseDouble(scanner.nextLine());
            double result;
            String unit;
            switch (choice) {
                case "1":
                    result = value * 9 / 5 + 32;
                    unit = "Fahrenheit";
                    break;
                case "2":
                    result = (value - 32) * 5 / 9;
                    unit = "Celsius";
                    break;
                case "3":
                    result = value + 273.15;
                    unit = "Kelvin";
                    break;
                case "4":
                    result = value - 273.15;
                    unit = "Celsius";
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
                    scanner.close();
                    return;
            }
            System.out.printf("Converted temperature: %.2f %s%n", result, unit);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric temperature.");
        } finally {
            scanner.close();
        }
    }
}
