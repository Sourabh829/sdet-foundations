import java.util.Scanner;

public class AreaOfCircle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the radius of the circle: ");
        if (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid numeric radius.");
            scanner.close();
            return;
        }
        double radius = scanner.nextDouble();
        scanner.close();
        if (radius < 0) {
            System.out.println("Radius cannot be negative.");
            return;
        }
        double area = Math.PI * radius * radius;
        System.out.printf("Area of the circle with radius %.2f is %.2f%n", radius, area);
    }
}
