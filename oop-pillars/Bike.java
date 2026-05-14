// INHERITANCE: Bike IS-A Vehicle — gets all of Vehicle's non-private members.
// Definition recap: The child class inherits state and behaviour from the parent.
//   It can also add its own fields (isElectric) and override inherited behaviour.
public class Bike extends Vehicle implements Refuelable {

    // ACCESS MODIFIER — default (package-private)
    // Definition: When NO access modifier is written, Java uses "default" access.
    //   Visible to all classes within the SAME package, but invisible outside the package.
    //   Less strict than private, more strict than protected.
    // Access modifier summary for quick reference:
    //   private         → this class only
    //   default         → this package only
    //   protected       → this package + all subclasses (even in other packages)
    //   public          → everywhere
    boolean isElectric;

    public Bike(String brand, int year, boolean isElectric) {
        super(brand, year, 15);  // INHERITANCE: calls Vehicle's constructor; bikes have smaller tanks
        this.isElectric = isElectric;
    }

    // POLYMORPHISM: overriding the abstract method from Vehicle.
    // Same method name getType() — each subclass returns its own answer.
    @Override
    public String getType() {
        return "Bike";
    }

    @Override
    public void describe() {
        String kind = isElectric ? "Electric" : "Petrol";
        System.out.println("[Bike] " + getBrand() + " | " + getYear()
            + " | Type: " + kind + " | Fuel: " + getFuelLevel() + "L");
    }

    // INHERITANCE in action: Bike does NOT override startEngine().
    // It simply inherits Vehicle's version and uses it as-is.
    // POLYMORPHISM point: when startEngine() is called on a Bike reference at runtime,
    //   Java walks up the chain, finds no override in Bike, and runs Vehicle's version.

    // POLYMORPHISM: Bike overrides refuel() to handle the electric edge case.
    // Same method name from the interface — very different behaviour based on object type.
    @Override
    public void refuel(int amount) {
        if (isElectric) {
            // Electric bikes charge, not refuel — the interface contract is still met,
            // but the implementation is completely different from Car or Truck.
            System.out.println("[Bike] " + getBrand() + " is electric — use a charger, not a pump.");
            return;
        }
        fuelLevel = Math.min(fuelLevel + amount, maxFuel);
        System.out.println("[Bike] Refuelled with " + amount + "L of " + fuelType()
            + ". Tank: " + fuelLevel + "/" + maxFuel + "L");
    }
    // fuelType() not overridden — inherits default "Petrol" from Refuelable.
}
