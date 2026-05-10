import java.util.Scanner;

public class BMICalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter your weight in kilograms: ");
            double weight = scanner.nextDouble();
            System.out.print("Enter your height in meters: ");
            double height = scanner.nextDouble();
            if (weight <= 0 || height <= 0) {
                System.out.println("Weight and height must be positive values.");
            } else {
                double bmi = weight / (height * height);
                System.out.printf("Your BMI is %.2f. Category: %s%n", bmi, bmiCategory(bmi));
            }
        } catch (Exception e) {
            System.out.println("Please enter valid numeric values for weight and height.");
        } finally {
            scanner.close();
        }
    }

    private static String bmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}
