/*
 * File: FixedDepositAccount.java
 * Purpose: Fixed deposit subtype with maturity-based withdrawal protection.
 * OOP concepts demonstrated: Inheritance, polymorphism, encapsulation.
 */
package model;

import java.time.LocalDate;

/*
 * Encapsulation protects the maturity constraint because callers cannot directly
 * change balance. They must call withdraw(), where this class can block early
 * withdrawals before the parent balance update happens.
 *
 * This override completely changes the parent behavior: BankAccount normally
 * allows valid withdrawals, but FixedDepositAccount refuses withdrawals before
 * maturity. The same withdraw() call is resolved polymorphically at runtime.
 *
 * Use IllegalArgumentException when the caller passes a bad value, such as a
 * negative amount. Use IllegalStateException when the value may be fine, but the
 * object is in a state that does not allow the operation, such as an unmatured FD.
 */
public class FixedDepositAccount extends BankAccount {

    private LocalDate maturityDate;
    private double interestRate;

    public FixedDepositAccount(String accountNumber, String holderName, double initialBalance,
            Address address, LocalDate maturityDate, double interestRate) {
        super(accountNumber, holderName, initialBalance, address);
        if (maturityDate == null) {
            throw new IllegalArgumentException("Maturity date is required.");
        }
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.maturityDate = maturityDate;
        this.interestRate = interestRate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (LocalDate.now().isBefore(maturityDate)) {
            throw new IllegalStateException("Fixed deposit withdrawal is blocked until maturity date: "
                    + maturityDate + ".");
        }
        super.withdraw(amount);
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Maturity: %s | Interest Rate: %.2f%%",
                maturityDate, interestRate * 100);
    }
}
