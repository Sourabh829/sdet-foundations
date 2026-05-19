/*
 * File: BankAccount.java
 * Purpose: Abstract base class for all account types.
 * OOP concepts demonstrated: Abstraction, encapsulation, inheritance support,
 * polymorphism hooks, composition, Comparable, equals/hashCode contract.
 */
package model;

import java.util.Objects;

/*
 * Four pillars mapping:
 * 1. Abstraction: this abstract class exposes common account behavior while
 *    forcing subclasses to implement calculateInterest().
 * 2. Encapsulation: private fields hide account state behind getters and
 *    controlled methods like deposit() and withdraw().
 * 3. Inheritance: SavingsAccount, CurrentAccount, and FixedDepositAccount reuse
 *    this shared account state and behavior.
 * 4. Polymorphism: non-final withdraw() and abstract calculateInterest() allow
 *    subclass-specific behavior through a BankAccount reference.
 *
 * balance has no setter because arbitrary external assignment would bypass
 * validation. The only legal balance changes go through deposit() and withdraw().
 *
 * withdraw() is intentionally not final. The Open-Closed Principle says code
 * should be open for extension but closed for modification, so subclasses can
 * extend withdrawal rules without editing this base class.
 *
 * equals() contract rules:
 * - Reflexive: x.equals(x) must be true.
 * - Symmetric: x.equals(y) and y.equals(x) must agree.
 * - Transitive: if x equals y and y equals z, x must equal z.
 * - Consistent: repeated calls should return the same answer while state is same.
 * - Null-check: x.equals(null) must be false.
 *
 * hashCode() must be consistent with equals(). If two equal accounts produce
 * different hash codes, HashSet may store duplicates and HashMap may fail to find
 * a value using an equal key.
 *
 * compareTo() uses accountNumber because it is the stable identity of an account,
 * making it a sensible natural ordering for Collections.sort().
 */
public abstract class BankAccount implements Comparable<BankAccount> {

    private String accountNumber;
    private String holderName;
    private double balance;
    private Address address;

    // Protected constructor blocks direct public construction but lets subclasses call super(...).
    protected BankAccount(String accountNumber, String holderName, double initialBalance, Address address) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required.");
        }
        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Holder name is required.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.address = address;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        adjustBalance(amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        adjustBalance(-amount);
    }

    // Protected helper keeps the field private; callers still change balance only through account operations.
    protected void adjustBalance(double amountChange) {
        balance += amountChange;
    }

    public abstract double calculateInterest();

    @Override
    public int compareTo(BankAccount other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BankAccount)) {
            return false;
        }
        BankAccount other = (BankAccount) obj;
        return Objects.equals(accountNumber, other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%s | Account: %s | Holder: %s | Balance: \u20B9%,.2f | Address: %s",
                getClass().getSimpleName(), accountNumber, holderName, balance, address);
    }
}
