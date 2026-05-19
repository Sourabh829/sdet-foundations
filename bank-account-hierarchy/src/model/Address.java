/*
 * File: Address.java
 * Purpose: Value object used by bank accounts to demonstrate composition.
 * OOP concepts demonstrated: Composition, encapsulation, Object equals/hashCode contract.
 */
package model;

import java.util.Objects;

/*
 * Composition means one class contains another object as part of its state.
 * A BankAccount "has an" Address because it stores an Address field.
 *
 * Address is NOT a parent of BankAccount. A bank account is not a kind of
 * address, so inheritance would model the relationship incorrectly. Inheritance
 * is an "is-a" relationship; composition is a "has-a" relationship.
 *
 * Address still needs correct equals() and hashCode() because parent objects may
 * compare or store composed values in collections. If a parent class includes an
 * Address in its own equality rules later, Address must already obey the same
 * object contract.
 */
public class Address {

    private String street;
    private String city;
    private String state;
    private String pinCode;

    public Address(String street, String city, String state, String pinCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPinCode() {
        return pinCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Address)) {
            return false;
        }
        Address other = (Address) obj;
        return Objects.equals(street, other.street)
                && Objects.equals(city, other.city)
                && Objects.equals(state, other.state)
                && Objects.equals(pinCode, other.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, pinCode);
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + state + " - " + pinCode;
    }
}
