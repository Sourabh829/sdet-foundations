// CUSTOM CHECKED EXCEPTION — thrown when a book exists but is already on loan.
// Definition: Having TWO separate exception types (BookNotFoundException vs BookNotAvailableException)
//   lets the caller handle each failure case differently in its own catch block.
//   A single generic Exception would force the caller to guess what went wrong.
// e.g. lendBook("ISBN-001", "M002") when ISBN-001 is already held by M001
//      → throws BookNotAvailableException("Clean Code", "M001")
//      → getMessage() = "Book not available: 'Clean Code' is currently on loan to M001"
public class BookNotAvailableException extends Exception {

    // The message includes the borrower's name so the librarian knows who has it.
    // e.g. new BookNotAvailableException("Clean Code", "Alice")
    //      → getMessage() = "Book not available: 'Clean Code' is currently on loan to Alice"
    public BookNotAvailableException(String title, String borrowerName) {
        super("Book not available: '" + title + "' is currently on loan to " + borrowerName);
    }
}
