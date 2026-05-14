// INHERITANCE — Pillar 3
// Definition: Inheritance is a mechanism where one class (child/subclass) acquires the
//   properties and behaviours of another class (parent/superclass).
//   The child gets all non-private fields and methods of the parent automatically.
// Keyword: extends — Car IS-A Vehicle. Every Car object also IS a Vehicle.
// Also implements Refuelable — a class can extend one class AND implement many interfaces.
public class Car extends Vehicle implements Refuelable {

    // ACCESS MODIFIER — private
    // Car-specific field. Even Vehicle (the parent) cannot see this directly.
    // Demonstrates that encapsulation applies within the hierarchy too.
    private int doors;

    // super(...) — calls the Vehicle constructor to initialise brand, year, maxFuel.
    // INHERITANCE rule: the parent's constructor is NOT inherited, but must be called explicitly.
    public Car(String brand, int year, int doors) {
        super(brand, year, 50);  // 50L tank for cars
        this.doors = doors;
    }

    // POLYMORPHISM — Pillar 4
    // Definition: Polymorphism means "many forms" — the same method name behaves differently
    //   depending on which object it is called on. Decided at RUNTIME, not compile time.
    // Two types:
    //   1. Overriding (runtime polymorphism) — subclass replaces a parent's method. Shown here.
    //   2. Overloading (compile-time polymorphism) — same method name, different parameters.
    // @Override annotation: tells the compiler "I intend to replace the parent's version".
    //   If the method signature doesn't match, the compiler will error — catches typos.
    @Override
    public String getType() {
        return "Car";
    }

    // Overriding abstract method — Car MUST provide this because Vehicle declared it abstract.
    // Each subclass prints its own unique details. Same method call, different output = polymorphism.
    @Override
    public void describe() {
        System.out.println("[Car] " + getBrand() + " | " + getYear()
            + " | Doors: " + doors + " | Fuel: " + getFuelLevel() + "L");
    }

    // POLYMORPHISM: overriding a CONCRETE method — Vehicle already had a working startEngine().
    // Car replaces it entirely. At runtime, if the object is a Car, this version runs.
    @Override
    public void startEngine() {
        System.out.println("[Car] " + getBrand() + " purrs smoothly to life.");
    }

    // Fulfilling the INTERFACE contract — refuel() was declared in Refuelable with no body.
    // Car must provide the body or the compiler refuses to compile.
    @Override
    public void refuel(int amount) {
        // fuelLevel and maxFuel are protected in Vehicle — accessible here because Car extends Vehicle
        fuelLevel = Math.min(fuelLevel + amount, maxFuel);
        System.out.println("[Car] Refuelled with " + amount + "L of " + fuelType()
            + ". Tank: " + fuelLevel + "/" + maxFuel + "L");
    }
    // fuelType() not overridden — Car inherits the default "Petrol" from the Refuelable interface.
}
