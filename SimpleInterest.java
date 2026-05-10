import java.util.Scanner;

public class SimpleInterest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the principal amount: ");
            double principal = scanner.nextDouble();
            System.out.print("Enter the annual interest rate (percent): ");
            double rate = scanner.nextDouble();
            System.out.print("Enter the time period in years: ");
            double time = scanner.nextDouble();
            if (principal < 0 || rate < 0 || time < 0) {
                System.out.println("Principal, rate, and time must all be non-negative.");
            } else {
                double simpleInterest = principal * rate * time / 100.0;
                double amount = principal + simpleInterest;
                System.out.printf("Simple Interest: %.2f%n", simpleInterest);
                System.out.printf("Total Amount after %.2f years: %.2f%n", time, amount);
            }
        } catch (Exception e) {
            System.out.println("Please enter valid numeric values for principal, rate, and time.");
        } finally {
            scanner.close();
        }
    }
}
