/*
 * File: SavingsAccount.java
 * Purpose: Savings account subtype with minimum balance and interest behavior.
 * OOP concepts demonstrated: Inheritance, polymorphism, constructor chaining.
 */
package model;

/*
 * super() performs constructor chaining: the subclass constructor delegates the
 * common account fields to BankAccount before setting savings-specific state.
 *
 * @Override tells the compiler this method must match a parent method. It catches
 * spelling mistakes and documents that runtime polymorphism is intended.
 *
 * Overriding rules:
 * - The access modifier cannot be more restrictive than the parent method.
 * - The return type must be the same or a covariant subtype.
 * - Checked exceptions cannot be broader than the parent method declares.
 *
 * This is runtime polymorphism. A BankAccount reference can point to a
 * SavingsAccount object, and the JVM uses dynamic dispatch to call this
 * withdraw() implementation at runtime.
 */
public class SavingsAccount extends BankAccount {

    private double minimumBalance;

    public SavingsAccount(String accountNumber, String holderName, double initialBalance,
            Address address, double minimumBalance) {
        super(accountNumber, holderName, initialBalance, address);
        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }
        this.minimumBalance = minimumBalance;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (getBalance() - amount < minimumBalance) {
            throw new IllegalArgumentException("Savings account must keep minimum balance of "
                    + String.format("\u20B9%,.2f", minimumBalance) + ".");
        }
        super.withdraw(amount);
    }

    @Override
    public double calculateInterest() {
        return getBalance() * 0.04;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Minimum Balance: \u20B9%,.2f", minimumBalance);
    }
}
