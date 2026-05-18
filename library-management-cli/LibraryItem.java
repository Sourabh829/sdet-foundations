// ABSTRACT CLASS — the shared blueprint for anything the library can hold.
// Definition: An abstract class cannot be instantiated directly (no new LibraryItem()).
//   It defines state and behaviour common to ALL library items, while forcing each
//   concrete subclass to fill in the details that only it can know.
// e.g. LIBBooks extends LibraryItem — LIBBooks IS-A LibraryItem and inherits itemId + title
//   automatically; it only needs to add its own fields (author, isbn, genre).
public abstract class LibraryItem {

    // ENCAPSULATION — private fields; only accessible through getters below.
    // Definition: Encapsulation hides internal state behind a controlled public interface.
    //   External code cannot corrupt itemId or title directly; they must go through getters.
    // e.g. book.getItemId() works; book.itemId = "X" would be a compile error.
    private String itemId;
    private String title;

    // Constructor called by subclasses via super(itemId, title).
    // e.g. LIBBooks calls super("B001", "Clean Code") → LibraryItem stores those two fields.
    public LibraryItem(String itemId, String title) {
        this.itemId = itemId;
        this.title  = title;
    }

    // PUBLIC GETTERS — the safe read-only window into private state.
    public String getItemId() { return itemId; }
    public String getTitle()  { return title; }

    // ABSTRACTION — abstract method: declares WHAT every item must do, not HOW.
    // Definition: abstract forces every concrete subclass to write its own implementation.
    //   LibraryItem says "every item has a category" but has no idea what LIBBooks's category is.
    // e.g. LIBBooks.getCategory() returns "Fiction" or "Non-Fiction" based on its own genre field.
    public abstract String getCategory();

    // POLYMORPHISM — concrete method that subclasses inherit and can override.
    // Definition: A concrete method in an abstract class provides shared behaviour.
    //   Subclasses get it for free, or override it for a custom version.
    // e.g. book.getSummary() → "[B001] Clean Code — Category: Non-Fiction"
    public String getSummary() {
        return "[" + itemId + "] " + title + " — Category: " + getCategory();
    }
}
