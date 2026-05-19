/*
 * File: Main.java
 * Purpose: Demo driver for the bank account hierarchy tutorial.
 * OOP concepts demonstrated: Composition, abstraction, encapsulation,
 * inheritance, polymorphism, Comparable, Comparator, equals/hashCode.
 *
 * Compilation and run instructions for bank-account-hierarchy only:
 *
 * cd bank-account-hierarchy
 * javac -d out src\model\*.java src\comparator\*.java src\service\*.java src\Main.java
 * java -cp out Main
 */

import comparator.AccountNameComparator;
import comparator.BalanceComparator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Address;
import model.BankAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import service.BankService;

public class Main {

        public static void main(String[] args) {
                System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

                printSection("SECTION 1: OBJECT CREATION (Constructors + Composition)");

                Address mumbaiAddress = new Address(
                                "12 MG Road, Fort",
                                "Mumbai",
                                "Maharashtra",
                                "400001");

                BankAccount savingsAccount = new SavingsAccount(
                                "SBI000123456789",
                                "Aarav Sharma",
                                550000.00,
                                mumbaiAddress,
                                100000.00);

                BankAccount currentAccount = new CurrentAccount(
                                "HDFC000987654321",
                                "Nisha Enterprises",
                                200000.00,
                                new Address("88 Residency Road", "Bengaluru", "Karnataka", "560025"),
                                200000.00);

                BankAccount fixedDepositAccount = new FixedDepositAccount(
                                "ICICI000456789123",
                                "Meera Iyer",
                                750000.00,
                                new Address("44 Anna Salai", "Chennai", "Tamil Nadu", "600002"),
                                LocalDate.now().plusYears(1),
                                0.0725);
                BankAccount savingsAccount1 = new SavingsAccount(
                                "SBI000123456789",
                                "raghav Sharma",
                                560000.00,
                                new Address("123 Kohinoor Marg", "Pune", "Maharashtra", "411057"),
                                120000.00);
                BankService.printAccountDetails(savingsAccount);
                BankService.printAccountDetails(savingsAccount1);
                BankService.printAccountDetails(currentAccount);
                BankService.printAccountDetails(fixedDepositAccount);

                printSection("SECTION 2: ENCAPSULATION DEMO");
                System.out.println("Holder via getter: " + savingsAccount.getHolderName());
                System.out.println("Balance via getter before operations: "
                                + BankService.formatAmount(savingsAccount.getBalance()));
                BankService.performDeposit(savingsAccount, 125000.00);
                BankService.performWithdraw(savingsAccount, 75000.00);
                System.out.println("Balance after controlled deposit/withdraw: "
                                + BankService.formatAmount(savingsAccount.getBalance()));
                System.out.println("No direct field access or public setBalance() is used.");

                printSection("SECTION 3: POLYMORPHISM - METHOD OVERRIDING");
                BankAccount savingsReference = savingsAccount;
                BankAccount currentReference = currentAccount;
                BankAccount fixedDepositReference = fixedDepositAccount;

                System.out.println("SavingsAccount below-minimum withdrawal attempt:");
                BankService.performWithdraw(savingsReference, 550000.00);

                System.out.println("CurrentAccount overdraft withdrawal attempt:");
                BankService.performWithdraw(currentReference, 350000.00);

                System.out.println("FixedDepositAccount before-maturity withdrawal attempt:");
                BankService.performWithdraw(fixedDepositReference, 50000.00);

                List<BankAccount> interestAccounts = new ArrayList<>();
                interestAccounts.add(savingsReference);
                interestAccounts.add(currentReference);
                interestAccounts.add(fixedDepositReference);
                System.out.println("Different calculateInterest() results through BankAccount references:");
                BankService.printInterestForAll(interestAccounts);

                printSection("SECTION 4: equals() AND hashCode() CONTRACT");
                BankAccount duplicateOne = new SavingsAccount(
                                "SBI000111222333",
                                "Rohan Verma",
                                300000.00,
                                mumbaiAddress,
                                100000.00);
                BankAccount duplicateTwo = new SavingsAccount(
                                "SBI000111222333",
                                "Rohan Verma",
                                900000.00,
                                new Address("9 Park Street", "Kolkata", "West Bengal", "700016"),
                                100000.00);

                System.out.println("duplicateOne.equals(duplicateTwo): " + duplicateOne.equals(duplicateTwo));
                System.out.println("duplicateOne.hashCode(): " + duplicateOne.hashCode());
                System.out.println("duplicateTwo.hashCode(): " + duplicateTwo.hashCode());

                Set<BankAccount> accountSet = new HashSet<>();
                accountSet.add(duplicateOne);
                accountSet.add(duplicateTwo);
                System.out.println("HashSet size after adding both equal accounts: " + accountSet.size());

                Map<BankAccount, String> accountLabels = new HashMap<>();
                accountLabels.put(duplicateOne, "Primary savings profile");
                System.out.println("HashMap get using second equal object: " + accountLabels.get(duplicateTwo));

                printSection("SECTION 5: Comparable (NATURAL ORDERING)");
                List<BankAccount> accounts = new ArrayList<>();
                accounts.add(fixedDepositAccount);
                accounts.add(savingsAccount);
                accounts.add(currentAccount);
                accounts.add(duplicateOne);

                System.out.println("Before Collections.sort():");
                printAccounts(accounts);
                Collections.sort(accounts);
                System.out.println("After Collections.sort() by accountNumber:");
                printAccounts(accounts);

                printSection("SECTION 6: Comparator (CUSTOM ORDERING)");
                accounts.sort(new BalanceComparator());
                System.out.println("Sorted by balance descending:");
                printAccounts(accounts);

                accounts.sort(new AccountNameComparator());
                System.out.println("Sorted by holderName, then balance descending:");
                printAccounts(accounts);

                accounts.sort((first, second) -> Double.compare(second.calculateInterest(), first.calculateInterest()));
                System.out.println("Sorted by interest amount descending using inline lambda:");
                printAccounts(accounts);

                accounts.sort(Comparator.comparing(BankAccount::getHolderName));
                System.out.println("Sorted by holderName using Comparator.comparing() and method reference:");
                printAccounts(accounts);

                printSection("SECTION 7: COMPOSITION vs INHERITANCE DEMO");
                System.out.println("Old savings address: " + savingsAccount.getAddress());
                savingsAccount.setAddress(new Address("7 Banjara Hills", "Hyderabad", "Telangana", "500034"));
                System.out.println("New savings address: " + savingsAccount.getAddress());
                System.out.println(
                                "If Address were a superclass, we couldn't have multiple addresses or swap them at runtime.");

                printSection("SECTION 8: instanceof AND TYPE CHECKING");
                for (BankAccount account : accounts) {
                        if (account instanceof SavingsAccount savings) {
                                System.out.println(account.getAccountNumber()
                                                + " is SavingsAccount with minimum balance "
                                                + BankService.formatAmount(savings.getMinimumBalance()));
                        } else if (account instanceof CurrentAccount current) {
                                System.out.println(account.getAccountNumber()
                                                + " is CurrentAccount with overdraft limit "
                                                + BankService.formatAmount(current.getOverdraftLimit()));
                        } else if (account instanceof FixedDepositAccount fixedDeposit) {
                                System.out.println(account.getAccountNumber() + " is FixedDepositAccount maturing on "
                                                + fixedDeposit.getMaturityDate());
                        }
                }

                System.out.println();
                System.out.println("Total balance across demo list: "
                                + BankService.formatAmount(BankService.getTotalBalance(accounts)));
                BankService.transfer(duplicateOne, savingsAccount, 125000.00);
        }

        private static void printSection(String title) {
                System.out.println();
                System.out.println("===== " + title + " =====");
        }

        private static void printAccounts(List<BankAccount> accounts) {
                for (BankAccount account : accounts) {
                        System.out.println(account.getAccountNumber()
                                        + " | " + account.getHolderName()
                                        + " | Balance: " + BankService.formatAmount(account.getBalance())
                                        + " | Interest: " + BankService.formatAmount(account.calculateInterest()));
                }
        }
}
