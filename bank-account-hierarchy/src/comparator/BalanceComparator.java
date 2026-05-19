/*
 * File: BalanceComparator.java
 * Purpose: External sorting strategy for BankAccount objects by balance.
 * OOP concepts demonstrated: Comparator, strategy-style custom ordering.
 */
package comparator;

import java.util.Comparator;
import model.BankAccount;

/*
 * Comparable vs Comparator:
 *
 * | Topic        | Comparable                         | Comparator                         |
 * |--------------|------------------------------------|------------------------------------|
 * | Package      | java.lang                          | java.util                          |
 * | Method       | compareTo(Object other)            | compare(Object one, Object two)    |
 * | Location     | Implemented inside the class       | Implemented outside the class      |
 * | Use case     | Natural/default ordering           | Custom/alternate ordering          |
 * | Example here | BankAccount sorts by accountNumber | BalanceComparator sorts by balance |
 *
 * Use Comparable when the class has one obvious natural order. Use Comparator
 * when you need multiple sorts or cannot modify the target class.
 *
 * Comparator is a functional interface because it has one abstract method,
 * compare(). That allows lambdas and method-reference based sorting.
 *
 * This class enables balance sorting without modifying BankAccount.
 */
public class BalanceComparator implements Comparator<BankAccount> {

    @Override
    public int compare(BankAccount first, BankAccount second) {
        return Double.compare(second.getBalance(), first.getBalance());
    }
}
