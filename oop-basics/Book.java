public class Book {
    private String title;
    private String author;
    private double price;
    private boolean isAvailable;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isAvailable = true;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }

    public void checkout() { isAvailable = false; }
    public void returnBook() { isAvailable = true; }

    @Override
    public String toString() {
        return String.format("'%s' by %s ($%.2f) [%s]",
            title, author, price, isAvailable ? "Available" : "Checked Out");
    }
}
