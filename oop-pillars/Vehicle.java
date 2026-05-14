// ABSTRACT CLASS: A partial blueprint — sits between a regular class and an interface.
// Definition: An abstract class cannot be instantiated directly (you can't do new Vehicle()).
//   It can contain abstract methods (no body — subclasses must implement) AND concrete methods
//   (with body — subclasses inherit or override). It can also hold fields and a constructor.
// Key rule: A class can extend only ONE abstract class.
// When to use: When subclasses share common state (fields) and some shared behaviour,
//   but each also needs to define certain behaviour their own way.
public abstract class Vehicle {

    // ENCAPSULATION — Pillar 1
    // Definition: Encapsulation is the practice of bundling data (fields) and the methods
    //   that operate on that data into a single unit (class), while restricting direct
    //   access to internal state from outside. Achieved via access modifiers.
    // Why: Prevents accidental corruption of data. Caller uses getters/setters — you control the rules.

    // ACCESS MODIFIER — private
    // Definition: private means visible ONLY inside this class. No subclass, no other class can touch it.
    // Used here to protect brand/year — they must be changed only through validated setters.
    private String brand;
    private int year;

    // ACCESS MODIFIER — protected
    // Definition: protected means visible to this class AND any subclass (Car, Bike, Truck),
    //   but NOT to unrelated classes outside the package.
    // Used here so subclasses can directly read/write fuelLevel during refuel() without needing a setter.
    protected int fuelLevel;
    protected int maxFuel;

    // Constructor — not abstract, runs when a subclass calls super(...)
    // Subclasses call super() to let Vehicle initialise the shared fields it owns.
    public Vehicle(String brand, int year, int maxFuel) {
        this.brand = brand;
        this.year = year;
        this.maxFuel = maxFuel;
        this.fuelLevel = maxFuel / 2;  // start half-full
    }

    // ACCESS MODIFIER — public
    // Definition: public means visible everywhere — any class, any package.
    // Getters are public because callers need read access, but writing goes through setters with rules.
    // ENCAPSULATION in action: brand is private, but getBrand() exposes it safely (read-only).
    public String getBrand() { return brand; }
    public int getYear()     { return year; }
    public int getFuelLevel(){ return fuelLevel; }

    // Setter with validation — the caller cannot set an impossible year.
    // This is the benefit of encapsulation: the class controls its own integrity.
    public void setYear(int year) {
        if (year >= 1886) this.year = year;  // first automobile was built in 1886
    }

    // Concrete method with a body — INHERITANCE means subclasses get this for free.
    // POLYMORPHISM: subclasses CAN override this with their own version (Car and Truck do).
    public void startEngine() {
        System.out.println(brand + " (" + year + ") engine started.");
    }

    // ABSTRACTION — Pillar 2
    // Definition: Abstraction means hiding implementation details and exposing only the essential
    //   interface. The caller knows WHAT a method does, not HOW it does it internally.
    // Here: Vehicle says "every vehicle must be describable and have a type" — but HOW each
    //   vehicle describes itself is left entirely to the subclass. Vehicle has zero say in the how.
    // abstract keyword: no body allowed here. Subclass is forced to write its own version.
    public abstract String getType();
    public abstract void describe();
}
