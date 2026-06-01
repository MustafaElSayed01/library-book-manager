# Library Book Manager

A command-line Java application for managing a library catalog. Users can add books, remove books, search by title or author, borrow and return books, and list everything currently stored in memory.

## Concepts Practiced

### OOP — Encapsulation
Private fields are exposed through getters, and mutable state is kept under control.
```java
public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private final Genre genre;
    private BookStatus bookStatus;

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public Genre getGenre() { return genre; }
    public BookStatus getBookStatus() { return bookStatus; }
}
```

---

### Input Validation with `try/catch`
Console input is validated before it reaches the service layer, and invalid choices are handled gracefully.
```java
try {
    int genreOption = Integer.parseInt(scanner.nextLine().trim());
    if (genreOption < 1 || genreOption > genres.length) {
        System.out.println("Invalid option, pick a valid genre.");
        return;
    }
} catch (NumberFormatException e) {
    System.out.println("Invalid input, enter a number");
}
```

---

### `Optional` for Safe Lookup
ISBN lookup uses `Optional` instead of `null` so missing books are handled safely.
```java
public Optional<Book> searchByIsbn(String isbn) {
    validateText(isbn, "isbn");
    for (Book book : books) {
        if (book.getIsbn().equals(isbn)) {
            return Optional.of(book);
        }
    }
    return Optional.empty();
}
```

---

### Single Responsibility
The application is split into focused classes: the UI handles console flow, the service handles catalog logic, and the model classes hold data.
```java
// ConsoleUI -> user interaction
// LibraryService -> catalog operations
// Book / Genre / BookStatus -> data model
```

---

### `StringBuilder` for Text Formatting
`StringBuilder` is used in the title-case helper to normalize book titles and author names efficiently.
```java
StringBuilder result = new StringBuilder();
for (String word : words) {
    result.append(word.substring(0, 1).toUpperCase())
          .append(word.substring(1).toLowerCase())
          .append(" ");
}
```

---

### Full Javadoc
The core classes and console methods are documented with clear method descriptions and parameter notes.
```java
/**
 * Prompts the user for book details and adds a new book to the catalog.
 *
 * <p>The method collects the title, author, ISBN, and genre from the console,
 * normalizes the title and author text, and creates a new {@code Book} with
 * an initial status of {@code AVAILABLE}.</p>
 */
public void addBook() { ... }
```

---

## Requirements

- Java 21
- Maven 3.9+ recommended

## How to Run

If you have Maven installed:

```bash
mvn clean compile
mvn exec:java
```

If you want to run the app from your IDE, start `com.library.Main`.

## Sample Output

```text
Starting Library Book Manager...
Welcome to Library Console UI
Pick a choice:
1. Add a new book
2. Remove existing book
3. Search for a book by Title
4. Search for a book by author
5. Borrow a book
6. Return a book
7. List all books
0. Quit
0
Thanks for using Library Book Management System
Library Book Manager closed.
```

## Notes

- Books are stored in memory, so the catalog resets when the application exits.
- Titles and author names are normalized into title case when adding a book.
- The service layer and console flows are covered by automated tests.
