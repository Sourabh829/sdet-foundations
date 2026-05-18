import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// LIBRARY — the core engine of the app. Brings together every concept covered:
//   Encapsulation  → private HashMaps; all state changes go through methods
//   Inheritance    → works with LIBBooks (child of LibraryItem) via polymorphism
//   Polymorphism   → calls book.getSummary() — runs LIBBooks's overridden version at runtime
//   Abstraction    → callers use addBook/lendBook/returnBook without knowing internal storage
//   HashMap        → O(1) lookup of books by ISBN and of members by ID
//   ArrayList      → ordered list returned by listAvailable() and searchByTitle()
//   HashSet        → unique genre list with no duplicates in getUniqueGenres()
//   throws         → lendBook and returnBook declare the exceptions they can fire
public class LIBLibrary {

    // HASHMAP — catalog: maps ISBN → LIBBooks for instant lookup.
    // Definition: HashMap<K,V> stores key-value pairs in a hash table.
    //   Keys are unique; adding the same key twice replaces the old entry.
    // e.g. catalog.put("978-0-13-235088-4", cleanCodeBook)
    //      catalog.get("978-0-13-235088-4") returns the LIBBooks object in O(1).
    private HashMap<String, LIBBooks> catalog;

    // HASHMAP — lendingLog: tracks which member currently holds each lent book.
    // key = ISBN of the lent book, value = memberId of the borrower.
    // e.g. lendingLog.put("978-0-13-235088-4", "M001")
    //      means member M001 has "Clean Code" on loan right now.
    private HashMap<String, String> lendingLog;

    // HASHMAP — members: registered library members, looked up by memberId.
    // e.g. members.put("M001", aliceMember) → members.get("M001") returns Alice.
    private HashMap<String, Member> members;

    public LIBLibrary() {
        catalog    = new HashMap<>();
        lendingLog = new HashMap<>();
        members    = new HashMap<>();
    }

    // OVERLOADED CONSTRUCTOR — accepts pre-built collections (useful for testing or seeding).
    // Definition: Constructor overloading lets callers choose how to initialise the object.
    //   Passing new ArrayList<>() and new HashMap<>() from Main starts the library empty,
    //   but the signature allows non-empty collections to be injected if needed later.
    // e.g. new Library(new ArrayList<>(), new HashMap<>()) → same as new Library() here,
    //      but makes the dependency on external collections explicit and visible to the caller.
    public LIBLibrary(ArrayList<LIBBooks> initialBooks, HashMap<String, String> initialLendingLog) {
        catalog    = new HashMap<>();
        lendingLog = initialLendingLog != null ? new HashMap<>(initialLendingLog) : new HashMap<>();
        members    = new HashMap<>();
        if (initialBooks != null) {
            for (LIBBooks b : initialBooks) catalog.put(b.getIsbn(), b);
        }
    }

    // --- BOOK MANAGEMENT ---

    // ADD BOOK — stores a LIBBooks object in the catalog keyed by its ISBN.
    // containsKey() checks for duplicates before adding.
    // e.g. addBook(cleanCodeBook) → catalog now has {"978-0-13-235088-4": cleanCodeBook}
    public void addBook(LIBBooks book) {
        if (catalog.containsKey(book.getIsbn())) {
            System.out.println("Already in catalog: " + book.getTitle());
            return;
        }
        catalog.put(book.getIsbn(), book);
        System.out.println("Added: " + book.getSummary());
    }

    // LEND BOOK — marks a book as on-loan and records who borrowed it.
    // `throws` declaration: tells the compiler (and every caller) that this method
    //   can fire BookNotFoundException OR BookNotAvailableException.
    //   The caller MUST catch both or declare throws itself — compiler enforced.
    // e.g. lendBook("978-0-13-235088-4", "M001")
    //      → book.setAvailable(false), lendingLog records isbn→"M001", member records isbn
    public void lendBook(String isbn, String memberId)
            throws BookNotFoundException, BookNotAvailableException {

        // THROW BookNotFoundException: ISBN not in catalog at all.
        // e.g. lendBook("FAKE-ISBN", "M001") → catalog.containsKey returns false → throw
        if (!catalog.containsKey(isbn)) {
            throw new BookNotFoundException(isbn);
        }

        LIBBooks book = catalog.get(isbn);

        // THROW BookNotAvailableException: book exists but is already lent out.
        // lendingLog.get(isbn) retrieves the current borrower's ID for the error message.
        // e.g. M001 already has "Clean Code"; M002 tries to borrow it → throw with M001's name
        if (!book.isAvailable()) {
            String borrowerId   = lendingLog.get(isbn);
            String borrowerName = members.containsKey(borrowerId)
                                  ? members.get(borrowerId).getName()
                                  : borrowerId;
            throw new BookNotAvailableException(book.getTitle(), borrowerName);
        }

        // All checks passed — perform the loan.
        book.setAvailable(false);
        lendingLog.put(isbn, memberId);

        if (members.containsKey(memberId)) {
            members.get(memberId).borrowBook(isbn);
        }

        System.out.println("Lent: \"" + book.getTitle() + "\" → member " + memberId);
    }

    // RETURN BOOK — marks a book as available and removes the lending record.
    // `throws BookNotFoundException`: returning an ISBN not in catalog is an error.
    // e.g. returnBook("978-0-13-235088-4") → book.setAvailable(true), lendingLog entry removed
    public void returnBook(String isbn) throws BookNotFoundException {

        if (!catalog.containsKey(isbn)) {
            throw new BookNotFoundException(isbn);
        }

        LIBBooks book = catalog.get(isbn);

        if (book.isAvailable()) {
            // Book exists but was never lent — no-op with a warning, not an exception.
            System.out.println("\"" + book.getTitle() + "\" was not on loan.");
            return;
        }

        // Remove the lending record and free up the member's slot.
        String borrowerId = lendingLog.remove(isbn);  // remove() deletes and returns the value
        book.setAvailable(true);

        if (members.containsKey(borrowerId)) {
            members.get(borrowerId).returnBook(isbn);
        }

        System.out.println("Returned: \"" + book.getTitle() + "\" — now available.");
    }

    // --- QUERIES ---

    // LIST AVAILABLE — returns an ArrayList of all books currently not on loan.
    // ARRAYLIST: ordered, allows duplicates, grows dynamically as books are added.
    // e.g. if catalog has 5 books and 2 are lent: listAvailable() returns ArrayList of 3 Books.
    public ArrayList<LIBBooks> listAvailable() {
        ArrayList<LIBBooks> available = new ArrayList<>();
        // catalog.values() returns all LIBBooks objects in the HashMap (no keys, just values).
        for (LIBBooks book : catalog.values()) {
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    // LIST ALL — all books regardless of availability.
    // POLYMORPHISM in use: book.getSummary() calls LIBBooks's overridden version at runtime,
    //   even though the return type is declared as LibraryItem's method.
    public ArrayList<LIBBooks> listAll() {
        return new ArrayList<>(catalog.values());
    }

    // SEARCH BY TITLE — case-insensitive partial match across entire catalog.
    // ARRAYLIST: results collected in insertion order; multiple matches supported.
    // e.g. searchByTitle("code") matches "Clean Code" and "The Code Book".
    public ArrayList<LIBBooks> searchByTitle(String keyword) {
        ArrayList<LIBBooks> results = new ArrayList<>();
        for (LIBBooks book : catalog.values()) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    // UNIQUE GENRES — HashSet of all distinct genres in the catalog.
    // HASHSET: automatically discards duplicates; adding "Fiction" twice stores it only once.
    // e.g. catalog has 3 "Non-Fiction" and 2 "Fiction" books
    //      → getUniqueGenres() returns {"Non-Fiction", "Fiction"} — only 2 entries.
    public HashSet<String> getUniqueGenres() {
        HashSet<String> genres = new HashSet<>();
        for (LIBBooks book : catalog.values()) {
            genres.add(book.getGenre());  // duplicate genres are silently ignored by HashSet
        }
        return genres;
    }

    // --- MEMBER MANAGEMENT ---

    // addMember — registers a new library member, stored in members HashMap by ID.
    // e.g. addMember(new Member("M001", "Alice")) → members.put("M001", aliceObject)
    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
        System.out.println("Registered member: " + member.getName());
    }

    public void listMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        System.out.println("\n--- Registered Members ---");
        for (Member m : members.values()) {
            System.out.println("  " + m);
        }
    }

    public int catalogSize() { return catalog.size(); }
}
