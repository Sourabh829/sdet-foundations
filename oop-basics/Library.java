import java.util.ArrayList;

public class Library {
    private String name;
    private ArrayList<Book> books;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Added: " + book.getTitle());
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) return book;
        }
        return null;
    }

    public boolean checkoutBook(String title) {
        Book book = findBook(title);
        if (book == null) {
            System.out.println("Book not found: " + title);
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("Already checked out: " + title);
            return false;
        }
        book.checkout();
        System.out.println("Checked out: " + title);
        return true;
    }

    public boolean returnBook(String title) {
        Book book = findBook(title);
        if (book == null) {
            System.out.println("Book not found: " + title);
            return false;
        }
        book.returnBook();
        System.out.println("Returned: " + title);
        return true;
    }

    public void listBooks() {
        System.out.println("\n--- " + name + " ---");
        if (books.isEmpty()) {
            System.out.println("No books.");
            return;
        }
        for (Book book : books) {
            System.out.println("  " + book);
        }
    }

    public static void main(String[] args) {
        Library lib = new Library("City Library");

        lib.addBook(new Book("Clean Code", "Robert Martin", 39.99));
        lib.addBook(new Book("The Pragmatic Programmer", "Hunt & Thomas", 44.99));
        lib.addBook(new Book("Effective Java", "Joshua Bloch", 49.99));

        lib.listBooks();

        lib.checkoutBook("Clean Code");
        lib.checkoutBook("Clean Code");  // already checked out
        lib.listBooks();

        lib.returnBook("Clean Code");
        lib.listBooks();
    }
}
