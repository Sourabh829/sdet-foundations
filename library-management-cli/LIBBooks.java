// INHERITANCE — LIBBooks IS-A LibraryItem; it extends the abstract blueprint.
// Definition: Inheritance lets a child class reuse the parent's fields and methods
//   without rewriting them. LIBBooks automatically gets itemId, title, getItemId(), getTitle(),
//   and getSummary() from LibraryItem — it only adds what makes a library book specifically a book.
// e.g. LIBBooks("B001", "Clean Code", "Robert Martin", "978-0-13-235088-4", "Non-Fiction")
//      → getSummary() inherited from LibraryItem → "[B001] Clean Code — Category: Non-Fiction"
public class LIBBooks extends LibraryItem {

    // ENCAPSULATION — LIBBooks-specific private fields.
    // These are invisible to LibraryItem and to any unrelated class.
    private String author;
    private String isbn;     // used as unique key in Library's HashMap
    private String genre;
    private boolean available;

    // super(itemId, title) — INHERITANCE rule: must call parent constructor first.
    // e.g. new LIBBooks("B001", "Clean Code", "Robert Martin", "978-0-13-235088-4", "Non-Fiction")
    //      → LibraryItem stores "B001" and "Clean Code"; LIBBooks stores the rest.
    public LIBBooks(String itemId, String title, String author, String isbn, String genre) {
        super(itemId, title);
        this.author    = author;
        this.isbn      = isbn;
        this.genre     = genre;
        this.available = true;  // every new book starts available
    }

    // OVERLOADED CONSTRUCTOR (3-arg) — title, author, isbn; genre defaults to "General".
    // Definition: Constructor overloading means multiple constructors with different parameter
    //   lists. Java picks the matching one based on argument count and types at call time.
    // e.g. new LIBBooks("Clean Code", "Robert C. Martin", "978-0-13-235088-4")
    //      → this(...) delegates to the 5-arg constructor; itemId auto-generated, genre = "General".
    // this(...) — calls another constructor in the same class, avoiding duplicated init logic.
    public LIBBooks(String title, String author, String isbn) {
        this(title, author, isbn, "General");
    }

    // OVERLOADED CONSTRUCTOR (4-arg) — title, author, isbn, genre; itemId auto-generated.
    // e.g. new LIBBooks("Clean Code", "Robert C. Martin", "978-0-13-235088-4", "Programming")
    //      → itemId built from the last 4 digits of the ISBN: "B-0884"
    public LIBBooks(String title, String author, String isbn, String genre) {
        this("B-" + isbn.replaceAll("[^0-9]", "")
                        .substring(Math.max(0, isbn.replaceAll("[^0-9]", "").length() - 4)),
             title, author, isbn, genre);
    }

    // POLYMORPHISM — overriding the abstract method from LibraryItem.
    // Definition: @Override replaces the parent's abstract declaration with a real body.
    //   Java decides at runtime which getCategory() to call based on the actual object type.
    // e.g. LibraryItem item = new LIBBooks(..., "Non-Fiction");
    //      item.getCategory() → calls LIBBooks's version → returns "Non-Fiction"
    @Override
    public String getCategory() {
        return genre;
    }

    // Getters — ENCAPSULATION: controlled read access to private fields.
    public String getAuthor()   { return author; }
    public String getIsbn()     { return isbn; }
    public String getGenre()    { return genre; }
    public boolean isAvailable(){ return available; }

    // Package-level setters used by Library to mark a book lent/returned.
    // ACCESS MODIFIER — default (no keyword): visible within same package, not outside.
    // e.g. Library.lendBook() calls book.setAvailable(false) to mark it on loan.
    void setAvailable(boolean available) { this.available = available; }

    // POLYMORPHISM — overriding getSummary() from LibraryItem for a richer Book-specific line.
    // LibraryItem's version was "[B001] Clean Code — Category: Non-Fiction"
    // LIBBooks's version adds author, isbn, and availability status.
    @Override
    public String getSummary() {
        String status = available ? "Available" : "On Loan";
        return String.format("[%s] \"%s\" by %s | ISBN: %s | Genre: %s | %s",
            getItemId(), getTitle(), author, isbn, genre, status);
    }
}
