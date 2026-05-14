// INHERITANCE: Truck IS-A Vehicle with extra cargo-specific state.
// All four pillars are visible across Car + Bike + Truck together:
//   Encapsulation  → private fields + getters/setters in Vehicle
//   Abstraction    → abstract methods in Vehicle (describe, getType)
//   Inheritance    → extends Vehicle; super() in constructor
//   Polymorphism   → @Override on startEngine, describe, refuel, fuelType
public class Truck extends Vehicle implements Refuelable {

    // private: Truck-specific detail. Parent and siblings cannot see this.
    private double cargoCapacityTons;

    public Truck(String brand, int year, double cargoCapacityTons) {
        super(brand, year, 200);  // INHERITANCE: trucks have much larger tanks than cars/bikes
        this.cargoCapacityTons = cargoCapacityTons;
    }

    // POLYMORPHISM (overriding): same abstract contract from Vehicle, unique Truck answer.
    @Override
    public String getType() {
        return "Truck";
    }

    @Override
    public void describe() {
        System.out.println("[Truck] " + getBrand() + " | " + getYear()
            + " | Cargo: " + cargoCapacityTons + "T | Fuel: " + getFuelLevel() + "L");
    }

    // POLYMORPHISM (overriding concrete): Vehicle's startEngine() existed, Truck replaces it.
    // Runtime polymorphism: if the variable is declared as Vehicle but holds a Truck object,
    //   calling startEngine() will execute THIS version — decided at runtime, not compile time.
    @Override
    public void startEngine() {
        System.out.println("[Truck] " + getBrand() + " roars and rumbles to life.");
    }

    @Override
    public void refuel(int amount) {
        fuelLevel = Math.min(fuelLevel + amount, maxFuel);
        System.out.println("[Truck] Refuelled with " + amount + "L of " + fuelType()
            + ". Tank: " + fuelLevel + "/" + maxFuel + "L");
    }

    // POLYMORPHISM: overriding a DEFAULT interface method.
    // Definition recap: A default method in an interface has a body — it's optional to override.
    //   Car and Bike don't bother; they inherit "Petrol". Truck overrides it because Trucks use Diesel.
    // Same method name fuelType(), called on different objects → different string returned = polymorphism.
    @Override
    public String fuelType() {
        return "Diesel";
    }
}
