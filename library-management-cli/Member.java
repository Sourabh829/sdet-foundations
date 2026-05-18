import java.util.ArrayList;

// MEMBER — represents a library member who can borrow books.
// Demonstrates ENCAPSULATION (private fields + getters) and ARRAYLIST usage.
public class Member {

    // ENCAPSULATION — private fields; external code cannot read or write them directly.
    private String memberId;
    private String name;

    // ARRAYLIST — ordered, resizable list of ISBNs this member currently holds.
    // Definition: ArrayList<E> is a dynamic array. It grows as items are added and shrinks
    //   when items are removed. Maintains insertion order. Allows duplicates.
    // e.g. after Alice borrows "ISBN-001" and "ISBN-002":
    //      borrowedIsbns = ["ISBN-001", "ISBN-002"] in the order she borrowed them.
    private ArrayList<String> borrowedIsbns;

    public Member(String memberId, String name) {
        this.memberId     = memberId;
        this.name         = name;
        this.borrowedIsbns = new ArrayList<>();  // starts empty; grows as books are borrowed
    }

    public String getMemberId() { return memberId; }
    public String getName()     { return name; }

    // Returns a COPY of the list so callers cannot modify the member's internal state.
    // ENCAPSULATION: returning a copy prevents outside code from adding/removing ISBNs
    //   without going through the proper borrowBook/returnBook methods below.
    public ArrayList<String> getBorrowedIsbns() {
        return new ArrayList<>(borrowedIsbns);
    }

    // ARRAYLIST — add(): appends the ISBN to the end of the list.
    // e.g. Alice borrows "ISBN-001" → borrowedIsbns.add("ISBN-001")
    //      borrowedIsbns is now ["ISBN-001"]
    void borrowBook(String isbn) {
        borrowedIsbns.add(isbn);
    }

    // ARRAYLIST — remove(): removes the first occurrence of the value (not by index).
    // e.g. Alice returns "ISBN-001" → borrowedIsbns.remove("ISBN-001")
    //      borrowedIsbns is now [] (empty again)
    void returnBook(String isbn) {
        borrowedIsbns.remove(isbn);
    }

    public int borrowedCount() {
        return borrowedIsbns.size();
    }

    @Override
    public String toString() {
        return "[" + memberId + "] " + name + " | Books on loan: " + borrowedIsbns.size();
    }
}
