/*
 * File: CurrentAccount.java
 * Purpose: Current account subtype with overdraft withdrawal behavior.
 * OOP concepts demonstrated: Inheritance, polymorphism, Liskov Substitution Principle.
 */
package model;

/*
 * CurrentAccount changes withdraw() compared with the parent and SavingsAccount:
 * - BankAccount allows withdrawal only up to current balance.
 * - SavingsAccount also protects a minimum balance.
 * - CurrentAccount allows the balance to go negative up to overdraftLimit.
 *
 * That is polymorphism in action: the same method call, withdraw(amount), behaves
 * differently based on the actual object type.
 *
 * Liskov Substitution Principle means a CurrentAccount should be usable anywhere
 * a BankAccount is expected without breaking the caller's assumptions. It still
 * validates positive amounts and reports invalid withdrawals with exceptions.
 */
public class CurrentAccount extends BankAccount {

    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String holderName, double initialBalance,
            Address address, double overdraftLimit) {
        super(accountNumber, holderName, initialBalance, address);
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > getBalance() + overdraftLimit) {
            throw new IllegalArgumentException("Withdrawal exceeds overdraft limit.");
        }
        adjustBalance(-amount);
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Overdraft Limit: \u20B9%,.2f", overdraftLimit);
    }
}
