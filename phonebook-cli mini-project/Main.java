import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

// EXCEPTION HANDLING
// Definition: Exception handling is Java's mechanism for responding to runtime errors gracefully
//   instead of crashing. It separates normal logic from error-handling logic.
//
// Keywords:
//   try     → wraps code that might throw an exception
//   catch   → runs only if the matching exception is thrown inside try
//   finally → ALWAYS runs after try/catch, whether or not an exception occurred (used for cleanup)
//   throw   → manually fires an exception object (used inside PhoneBook.search/delete)
//   throws  → declares that a method may throw an exception (used in PhoneBook method signatures)
//
// Flow example in this app:
//   search("Ghost") → PhoneBook.search() throws ContactNotFoundException
//   → execution jumps to the matching catch block → finally runs → program continues normally
public class Main {

    public static void main(String[] args) {
        PhoneBook phonebook = new PhoneBook();
        Scanner sc = new Scanner(System.in);

        // Pre-load a few contacts so the app is usable immediately on first run.
        phonebook.add("Alice",   "9876543210");
        phonebook.add("Bob",     "9812345678");
        phonebook.add("Charlie", "9876001122");

        System.out.println("\n=== PhoneBook CLI ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Add  2. Search  3. Delete  4. List All  5. Area Codes  6. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1":
                    System.out.print("Name: ");
                    String addName = sc.nextLine().trim();
                    System.out.print("Phone: ");
                    String addPhone = sc.nextLine().trim();
                    phonebook.add(addName, addPhone);
                    break;

                case "2":
                    System.out.print("Search name: ");
                    String searchName = sc.nextLine().trim();

                    // TRY/CATCH/FINALLY — exception handling in action.
                    // Definition of try: the block that contains code which might fail.
                    //   e.g. phonebook.search(searchName) throws ContactNotFoundException
                    //   if the name is not in the HashMap — we put it inside try to handle that.
                    try {
                        String phone = phonebook.search(searchName);
                        System.out.println("Found: " + searchName + " → " + phone);
                    }
                    // Definition of catch: runs ONLY if the specified exception was thrown in try.
                    //   e.g. searching "Ghost" throws ContactNotFoundException →
                    //   execution skips the rest of try and jumps here.
                    //   `e` holds the exception object; e.getMessage() returns "Contact not found: Ghost"
                    catch (ContactNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    // Definition of finally: runs ALWAYS — after try if no exception,
                    //   or after catch if an exception was thrown. Cannot be skipped.
                    //   Used for cleanup (closing files, releasing resources).
                    //   e.g. here it confirms the search attempt is complete regardless of outcome.
                    finally {
                        System.out.println("[finally] Search attempt complete.");
                    }
                    break;

                case "3":
                    System.out.print("Delete name: ");
                    String delName = sc.nextLine().trim();

                    // Same try/catch pattern — delete() also throws ContactNotFoundException.
                    // MULTIPLE CATCH: you can chain catch blocks to handle different exception types.
                    //   e.g. catch (ContactNotFoundException e) { ... }
                    //        catch (Exception e) { ... }  ← broader fallback for unexpected errors
                    try {
                        phonebook.delete(delName);
                    }
                    catch (ContactNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    finally {
                        System.out.println("[finally] Delete attempt complete.");
                    }
                    break;

                case "4":
                    // ARRAYLIST in use: listAllContacts() returns ArrayList<String>.
                    // Definition recap: ArrayList maintains order, allows duplicates, grows automatically.
                    // e.g. list = ["Alice: 9876543210", "Bob: 9812345678", "Charlie: 9876001122"]
                    ArrayList<String> list = phonebook.listAllContacts();
                    if (list.isEmpty()) {
                        System.out.println("Phonebook is empty.");
                    } else {
                        System.out.println("\n--- All Contacts (" + phonebook.size() + ") ---");
                        for (String entry : list) {
                            System.out.println("  " + entry);
                        }
                    }
                    break;

                case "5":
                    // HASHSET in use: getUniqueAreaCodes() returns HashSet<String>.
                    // Definition recap: HashSet stores only unique values, no duplicates, unordered.
                    // e.g. Alice "9876..." and Charlie "9876..." both have prefix "9876" →
                    //      HashSet shows it only once alongside Bob's "9812".
                    HashSet<String> codes = phonebook.getUniqueAreaCodes();
                    System.out.println("Unique area codes (first 4 digits): " + codes);
                    break;

                case "6":
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid choice. Enter 1–6.");
            }
        }

        // finally-equivalent cleanup: Scanner wraps System.in, should always be closed.
        sc.close();
    }
}
