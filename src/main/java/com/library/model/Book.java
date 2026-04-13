package com.library.model;

/**
 * Represents a single book in the library catalog.
 *
 * <p>Each book stores its title, author, ISBN, genre, and current availability
 * status. Instances of this class are used by the service layer to manage
 * catalog operations such as adding, searching, borrowing, and returning books.</p>
 */
public class Book {

    /**
     * The title of the book.
     */
    private final String title;

    /**
     * The name of the book's author.
     */
    private final String author;

    /**
     * The ISBN used to uniquely identify the book.
     */
    private final String isbn;

    /**
     * The genre assigned to the book.
     */
    private final Genre genre;

    /**
     * The current availability status of the book.
     */
    private BookStatus bookStatus;

    /**
     * Creates a new book record.
     *
     * @param title      the title of the book
     * @param author     the author of the book
     * @param isbn       the ISBN of the book
     * @param genre      the genre of the book
     * @param bookStatus the current status of the book
     * @throws IllegalArgumentException if any required argument is null or blank
     */
    public Book(String title, String author, String isbn, Genre genre, BookStatus bookStatus) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title cannot be null or blank");
        if (author == null || author.isBlank())
            throw new IllegalArgumentException("author cannot be null or blank");
        if (isbn == null || isbn.isBlank())
            throw new IllegalArgumentException("ISBN cannot be null or blank");

        if (genre == null) throw new IllegalArgumentException("genre cannot be null");
        if (bookStatus == null) throw new IllegalArgumentException("bookStatus cannot be null");
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.bookStatus = bookStatus;
    }

    /**
     * Returns the title of the book.
     *
     * @return the book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     *
     * @return the book author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return the book ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the genre of the book.
     *
     * @return the book genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Returns the current status of the book.
     *
     * @return the book status
     */
    public BookStatus getBookStatus() {
        return bookStatus;
    }

    /**
     * Updates the current availability status of the book.
     *
     * @param bookStatus the new status to assign
     * @throws IllegalArgumentException if {@code bookStatus} is null
     */
    public void setStatus(BookStatus bookStatus) {
        if (bookStatus == null) throw new IllegalArgumentException("bookStatus cannot be null");
        this.bookStatus = bookStatus;
    }
}
