import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

// MAIN — console CLI that ties every concept together.
// EXCEPTION HANDLING recap:
//   try     → wraps code that might throw; if exception fires, remaining try lines are skipped
//   catch   → catches a specific exception type and handles it gracefully (no crash)
//   finally → ALWAYS runs after try/catch, whether or not an exception occurred
//   throw   → fires an exception (used in Library.lendBook / Library.returnBook)
//   throws  → method signature declaration (used in Library methods — not here, since main catches)
//
// MULTIPLE CATCH BLOCKS: lendBook() can throw two different exception types.
//   Each catch handles a different failure reason with a different message.
//   e.g. catch (BookNotFoundException e)    → ISBN doesn't exist in catalog
//        catch (BookNotAvailableException e) → ISBN exists but book is already on loan
public class LIBMain {

    public static void main(String[] args) {

        LIBLibrary library = new LIBLibrary(new ArrayList<>(), new HashMap<>());
        Scanner sc = new Scanner(System.in);

        // Seed the catalog with books so the app is usable immediately.
        // INHERITANCE + POLYMORPHISM: LIBBooks extends LibraryItem; addBook accepts a LIBBooks
        // and calls book.getSummary() → runs LIBBooks's overridden version at runtime.
        library.addBook(new LIBBooks("Clean Code", "Robert C. Martin", "978-0-13-235088-4", "Programming"));
        library.addBook(new LIBBooks("The Pragmatic Programmer", "Hunt & Thomas", "978-0-13-595705-9", "Programming"));
        library.addBook(new LIBBooks("Effective Java", "Joshua Bloch", "978-0-13-468599-1", "Programming"));
        library.addBook(new LIBBooks("Dune", "Frank Herbert", "978-0-44-101359-7", "Science Fiction"));
        library.addBook(new LIBBooks("1984", "George Orwell", "978-0-45-228423-4", "Dystopian"));

        // Register members — stored in Library's HashMap<String, Member>.
        library.addMember(new Member("M001", "Alice"));
        library.addMember(new Member("M002", "Bob"));

        System.out.println("\n=== Library Management System ===");

        boolean running = true;
        while (running) {
            System.out.println("""
                    \n--- Menu ---
                    1. List available books
                    2. List all books
                    3. Search by title
                    4. Lend a book
                    5. Return a book
                    6. Unique genres
                    7. List members
                    8. Exit""");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {

                // ARRAYLIST — listAvailable() returns ArrayList<LIBBooks>; we iterate and print
                // each.
                // POLYMORPHISM: book.getSummary() calls Book's overridden version for each
                // element.
                case "1" -> {
                    ArrayList<LIBBooks> available = library.listAvailable();
                    if (available.isEmpty()) {
                        System.out.println("No books currently available.");
                    } else {
                        System.out.println("\n--- Available Books ---");
                        for (LIBBooks b : available)
                            System.out.println("  " + b.getSummary());
                    }
                }

                case "2" -> {
                    ArrayList<LIBBooks> all = library.listAll();
                    System.out.println("\n--- All Books (" + all.size() + ") ---");
                    for (LIBBooks b : all)
                        System.out.println("  " + b.getSummary());
                }

                // ARRAYLIST — searchByTitle() returns ArrayList of partial-match results.
                // e.g. keyword "code" matches both "Clean Code" and "The Code Book".
                case "3" -> {
                    System.out.print("Search keyword: ");
                    String keyword = sc.nextLine().trim();
                    ArrayList<LIBBooks> results = library.searchByTitle(keyword);
                    if (results.isEmpty()) {
                        System.out.println("No books found matching: " + keyword);
                    } else {
                        for (LIBBooks b : results)
                            System.out.println("  " + b.getSummary());
                    }
                }

                // EXCEPTION HANDLING — lendBook can throw two different checked exceptions.
                // TRY: attempt the loan — may succeed or throw one of two exceptions.
                // CATCH 1 (BookNotFoundException): ISBN is not in catalog at all.
                // e.g. user enters "FAKE-ISBN" → catch fires, prints "Book not found:
                // FAKE-ISBN"
                // CATCH 2 (BookNotAvailableException): ISBN exists but already on loan.
                // e.g. Alice has "Clean Code"; Bob tries to borrow it → catch fires with
                // Alice's name
                // FINALLY: confirmation line always prints, even after an error.
                case "4" -> {
                    System.out.print("ISBN to lend: ");
                    String isbn = sc.nextLine().trim();
                    System.out.print("Member ID:    ");
                    String memberId = sc.nextLine().trim();

                    try {
                        library.lendBook(isbn, memberId);
                    } catch (BookNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (BookNotAvailableException e) {
                        System.out.println("Error: " + e.getMessage());
                    } finally {
                        // FINALLY: runs regardless of success or failure — good for audit/logging.
                        System.out.println("[finally] Lend request processed.");
                    }
                }

                // EXCEPTION HANDLING — returnBook can throw BookNotFoundException.
                // TRY: attempt the return. CATCH: ISBN not in catalog. FINALLY: always logs.
                case "5" -> {
                    System.out.print("ISBN to return: ");
                    String isbn = sc.nextLine().trim();

                    try {
                        library.returnBook(isbn);
                    } catch (BookNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } finally {
                        System.out.println("[finally] Return request processed.");
                    }
                }

                // HASHSET — getUniqueGenres() returns HashSet<String> with no duplicates.
                // e.g. catalog has 3 "Non-Fiction" books → HashSet shows "Non-Fiction" only
                // once.
                case "6" -> {
                    HashSet<String> genres = library.getUniqueGenres();
                    System.out.println("Genres in catalog: " + genres);
                }

                case "7" -> library.listMembers();

                case "8" -> {
                    running = false;
                    System.out.println("Goodbye.");
                }

                default -> System.out.println("Invalid choice. Enter 1-8.");
            }
        }

        sc.close();
    }
}
