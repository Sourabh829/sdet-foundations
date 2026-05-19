/*
 * File: AccountNameComparator.java
 * Purpose: Chained sorting strategy for BankAccount objects.
 * OOP concepts demonstrated: Comparator chaining, method references.
 */
package comparator;

import java.util.Comparator;
import model.BankAccount;

/*
 * Comparator chaining means one comparison is tried first, and a second
 * comparison is used only when the first one ties.
 *
 * BankAccount::getHolderName is a method reference. It is shorthand for a lambda
 * such as account -> account.getHolderName().
 *
 * Java 8+ Comparator static/default methods like comparing(), comparingDouble(),
 * reversed(), and thenComparing() make multi-field sorting readable and reusable.
 */
public class AccountNameComparator implements Comparator<BankAccount> {

    @Override
    public int compare(BankAccount first, BankAccount second) {
        return Comparator.comparing(BankAccount::getHolderName)
                .thenComparing(Comparator.comparingDouble(BankAccount::getBalance).reversed())
                .compare(first, second);
    }
}
