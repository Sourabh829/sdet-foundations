public class Main {
    public static void main(String[] args) {

        // POLYMORPHISM — runtime (dynamic dispatch)
        // Definition: Each element is declared as type Vehicle (the parent reference),
        //   but the actual object stored is Car, Bike, or Truck.
        // At runtime Java looks at the REAL object type, not the declared type, to decide
        //   which method version to call. This decision happens at runtime — hence "runtime polymorphism".
        Vehicle[] fleet = {
            new Car("Toyota", 2022, 4),
            new Bike("Honda", 2023, true),
            new Bike("Triumph", 2024, false),
            new Truck("Volvo", 2021, 12.5)
        };

        // ABSTRACTION in use: we call describe() without knowing HOW each vehicle implements it.
        // The caller (this loop) is shielded from the details — it just calls the method.
        // POLYMORPHISM: same line v.describe() produces different output for each object type.
        System.out.println("=== Fleet Descriptions ===");
        for (Vehicle v : fleet) {
            v.describe();  // Car's version, Bike's version, or Truck's version — decided at runtime
        }

        // POLYMORPHISM: startEngine() — Car overrides it, Truck overrides it, Bike does NOT.
        // When v is a Bike, Java walks up to Vehicle and runs Vehicle's startEngine().
        // When v is a Car or Truck, Java finds the override and runs that instead.
        System.out.println("\n=== Starting Engines ===");
        for (Vehicle v : fleet) {
            v.startEngine();
        }

        // INTERFACE usage: Vehicle reference doesn't know about refuel() — it's not in Vehicle.
        // We must check if the object also IS-A Refuelable before calling it.
        // instanceof with pattern matching (Java 16+): checks type AND casts in one line.
        // Definition of instanceof: returns true if the object is an instance of the specified type.
        System.out.println("\n=== Refuelling ===");
        for (Vehicle v : fleet) {
            if (v instanceof Refuelable r) {  // r is already cast — no separate (Refuelable) v needed
                r.refuel(20);  // Car and Bike use Petrol logic; Truck uses Diesel; electric Bike refuses
            }
        }

        // ABSTRACTION: getType() is abstract in Vehicle — we call it without knowing the implementation.
        // POLYMORPHISM: each subclass returns its own string for the same method call.
        System.out.println("\n=== Type Check ===");
        for (Vehicle v : fleet) {
            System.out.println(v.getBrand() + " is a " + v.getType());
        }
    }
}
