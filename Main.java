import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Book class with title, author, and ISBN attributes
class Book {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title  = title;
        this.author = author;
        this.isbn   = isbn;
    }

    // Getters
    public String getTitle()  { return title; }
    public String getAuthor() { return author; }
    public String getIsbn()   { return isbn; }

    // Setters
    public void setTitle(String title)   { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn)     { this.isbn = isbn; }

    @Override
    public String toString() {
        return String.format("Book [Title: \"%s\", Author: %s, ISBN: %s]", title, author, isbn);
    }
}

// BookCollection class to manage a collection of books
class BookCollection {
    private List<Book> books;
    private String collectionName;

    public BookCollection(String collectionName) {
        this.collectionName = collectionName;
        this.books = new ArrayList<>();
    }

    // Add a book to the collection
    public boolean addBook(Book book) {
        if (book == null) {
            System.out.println("Cannot add a null book.");
            return false;
        }
        // Prevent duplicate ISBNs
        if (findByIsbn(book.getIsbn()).isPresent()) {
            System.out.println("A book with ISBN " + book.getIsbn() + " already exists in the collection.");
            return false;
        }
        books.add(book);
        System.out.println("Added: " + book);
        return true;
    }

    // Remove a book by ISBN
    public boolean removeBookByIsbn(String isbn) {
        Optional<Book> book = findByIsbn(isbn);
        if (book.isPresent()) {
            books.remove(book.get());
            System.out.println("Removed: " + book.get());
            return true;
        }
        System.out.println("No book found with ISBN: " + isbn);
        return false;
    }

    // Remove a book by title (removes first match)
    public boolean removeBookByTitle(String title) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst();
        if (book.isPresent()) {
            books.remove(book.get());
            System.out.println("Removed: " + book.get());
            return true;
        }
        System.out.println("No book found with title: \"" + title + "\"");
        return false;
    }

    // Find a book by ISBN
    public Optional<Book> findByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst();
    }

    // Display all books in the collection
    public void displayAll() {
        if (books.isEmpty()) {
            System.out.println("The collection \"" + collectionName + "\" is empty.");
            return;
        }
        System.out.println("\n=== " + collectionName + " (" + books.size() + " books) ===");
        books.forEach(b -> System.out.println("  " + b));
        System.out.println();
    }

    public int getSize() { return books.size(); }
}

// Main class to demonstrate the functionality
public class Main {
    public static void main(String[] args) {

        BookCollection library = new BookCollection("My Library");

        // --- Adding books ---
        System.out.println("--- Adding Books ---");
        library.addBook(new Book("The Great Gatsby",       "F. Scott Fitzgerald", "978-0743273565"));
        library.addBook(new Book("To Kill a Mockingbird",  "Harper Lee",          "978-0061935466"));
        library.addBook(new Book("1984",                   "George Orwell",       "978-0451524935"));
        library.addBook(new Book("The Catcher in the Rye", "J.D. Salinger",       "978-0316769488"));

        // Try adding a duplicate ISBN
        library.addBook(new Book("Duplicate Book", "Some Author", "978-0451524935"));

        library.displayAll();

        // --- Removing books ---
        System.out.println("--- Removing Books ---");
        library.removeBookByIsbn("978-0743273565");         // Remove by ISBN
        library.removeBookByTitle("To Kill a Mockingbird"); // Remove by title
        library.removeBookByIsbn("000-0000000000");         // Non-existent ISBN

        library.displayAll();
    }
}