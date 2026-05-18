// CUSTOM CHECKED EXCEPTION — thrown when a book ISBN is not found in the catalog.
// Definition: A checked exception extends Exception (not RuntimeException).
//   The compiler enforces that every caller either catches it or declares `throws`.
//   Used here because "book not found" is a recoverable, expected error the caller must handle.
// e.g. Library.lendBook("UNKNOWN-ISBN", "M001") → throws BookNotFoundException("UNKNOWN-ISBN")
//      → catch block in Main.java prints e.getMessage() = "Book not found: UNKNOWN-ISBN"
public class BookNotFoundException extends Exception {

    // super(message) passes the error text up to Exception so getMessage() can return it.
    // e.g. new BookNotFoundException("978-0-13-468599-1").getMessage()
    //      returns "Book not found: 978-0-13-468599-1"
    public BookNotFoundException(String isbn) {
        super("Book not found: " + isbn);
    }
}
