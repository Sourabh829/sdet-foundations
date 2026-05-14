// INTERFACE: A pure contract that defines WHAT a class must do, not HOW.
// Definition: An interface is a reference type in Java — a collection of abstract
//   method signatures (and optional default/static methods) that a class agrees to implement.
//   It cannot have instance fields or constructors.
// Key rule: A class can implement MULTIPLE interfaces (Java's answer to multiple inheritance).
//   e.g. class Car extends Vehicle implements Refuelable, Trackable, Insurable { ... }
// Interface vs Abstract Class:
//   - Interface  → pure contract, no state, multiple allowed, use when unrelated classes share behaviour
//   - Abstract   → partial blueprint, can have state + logic, only one, use for IS-A relationships
public interface Refuelable {

    // Abstract method — no body here, just the signature.
    // Every class that says "implements Refuelable" MUST write its own refuel() body.
    void refuel(int amount);

    // Default method (introduced Java 8) — has a body, acts as a fallback.
    // Definition: A default method lets an interface provide a base implementation
    //   so existing implementing classes don't break when new methods are added.
    // Implementing classes can override it (Truck does) or inherit it as-is (Car, Bike do).
    default String fuelType() {
        return "Petrol";
    }
}
