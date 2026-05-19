/*
 * File: BankService.java
 * Purpose: Static utility methods that operate on the BankAccount abstraction.
 * OOP concepts demonstrated: Polymorphism, abstraction, Open-Closed Principle.
 */
package service;

import java.util.List;
import model.BankAccount;

/*
 * Parameters use BankAccount, not SavingsAccount, because callers should program
 * to the abstraction. The same service method can work with savings, current,
 * fixed deposit, and future account types.
 *
 * At runtime, the JVM resolves overridden methods through dynamic dispatch
 * (often implemented with a vtable). For account.withdraw(amount), the object
 * type decides which withdraw() method actually runs.
 *
 * This follows the Open-Closed Principle: adding a new account type should not
 * require changes in this service, as long as the new type extends BankAccount
 * and implements the expected behavior.
 */
public final class BankService {

    private BankService() {
    }

    public static void printAccountDetails(BankAccount account) {
        System.out.println(account);
    }

    public static void performDeposit(BankAccount account, double amount) {
        try {
            account.deposit(amount);
            System.out.println("Deposited " + formatAmount(amount) + " into "
                    + account.getAccountNumber() + ". New balance: " + formatAmount(account.getBalance()));
        } catch (IllegalArgumentException ex) {
            System.out.println("Deposit failed for " + account.getAccountNumber() + ": " + ex.getMessage());
        }
    }

    public static void performWithdraw(BankAccount account, double amount) {
        try {
            account.withdraw(amount);
            System.out.println("Withdrew " + formatAmount(amount) + " from "
                    + account.getAccountNumber() + ". New balance: " + formatAmount(account.getBalance()));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("Withdrawal failed for " + account.getAccountNumber() + ": " + ex.getMessage());
        }
    }

    public static void transfer(BankAccount from, BankAccount to, double amount) {
        try {
            from.withdraw(amount);
            to.deposit(amount);
            System.out.println("Transferred " + formatAmount(amount) + " from "
                    + from.getAccountNumber() + " to " + to.getAccountNumber() + ".");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("Transfer failed: " + ex.getMessage());
        }
    }

    public static double getTotalBalance(List<BankAccount> accounts) {
        double total = 0.0;
        for (BankAccount account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    public static void printInterestForAll(List<BankAccount> accounts) {
        for (BankAccount account : accounts) {
            System.out.println(account.getAccountNumber() + " (" + account.getClass().getSimpleName()
                    + ") interest: " + formatAmount(account.calculateInterest()));
        }
    }

    public static String formatAmount(double amount) {
        return String.format("\u20B9%,.2f", amount);
    }
}
