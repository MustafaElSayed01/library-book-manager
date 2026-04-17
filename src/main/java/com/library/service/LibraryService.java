package com.library.service;

import com.library.model.Book;
import com.library.model.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides catalog management operations for the library application.
 *
 * <p>This service should own the collection of books and implement behaviors for
 * adding, listing, searching, borrowing, returning, and removing catalog items.</p>
 */
public class LibraryService {
    /**
     * The in-memory collection of books managed by the service.
     */
    private final List<Book> books = new ArrayList<>();

    /**
     * Adds a new book to the catalog if its ISBN is not already present.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if {@code book} is null or a book with the same ISBN already exists
     */
    public void add(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book cannot be null");
        }
        Optional<Book> isExist = searchByIsbn(book.getIsbn());
        if (isExist.isPresent()) {
            throw new IllegalArgumentException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        books.add(book);
    }

    /**
     * Removes a book from the catalog using its ISBN.
     *
     * @param isbn the ISBN of the book to remove
     * @throws IllegalArgumentException if {@code isbn} is null, blank, or no matching book is found
     */
    public void remove(String isbn) {
        Optional<Book> targetBook = searchByIsbn(isbn);
        if (targetBook.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }
        books.remove(targetBook.get());
    }

    /**
     * Searches for a book by its ISBN.
     *
     * @param isbn the ISBN to search for
     * @throws IllegalArgumentException if {@code isbn} is null or blank
     * @return an {@link Optional} containing the matching book, or empty if not found
     */
    public Optional<Book> searchByIsbn(String isbn) {
        validateText(isbn, "isbn");
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    /**
     * Searches for books whose titles contain the given text.
     *
     * @param title the title text to match, ignoring case
     * @throws IllegalArgumentException if {@code title} is null or blank
     * @return a list of books with titles containing the search text
     */
    public List<Book> searchByTitle(String title) {
        validateText(title, "title");
        String normalizedTitle = title.trim().toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(normalizedTitle)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Searches for books whose authors contain the given text.
     *
     * @param author the author text to match, ignoring case
     * @throws IllegalArgumentException if {@code author} is null or blank
     * @return a list of books with authors containing the search text
     */
    public List<Book> searchByAuthor(String author) {
        validateText(author, "author");
        String normalizedAuthor = author.trim().toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(normalizedAuthor)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Marks a book as borrowed if it is currently available.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if {@code isbn} is null, blank, the book is not found, or it is already borrowed
     */
    public void borrowBook(String isbn) {
        Optional<Book> targetBook = searchByIsbn(isbn);
        if (targetBook.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }
        Book book = targetBook.get();
        if (book.getBookStatus().equals(BookStatus.BORROWED)) {
            throw new IllegalArgumentException("Book is already borrowed");
        }
        book.setStatus(BookStatus.BORROWED);
    }

    /**
     * Marks a borrowed book as available again.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if {@code isbn} is null, blank, the book is not found, or it is already available
     */
    public void returnBook(String isbn) {
        Optional<Book> targetBook = searchByIsbn(isbn);
        if (targetBook.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }
        Book book = targetBook.get();
        if (book.getBookStatus().equals(BookStatus.AVAILABLE)) {
            throw new IllegalArgumentException("Book is already returned");
        }
        book.setStatus(BookStatus.AVAILABLE);
    }

    /**
     * Returns a copy of all books currently in the catalog.
     *
     * @return a new list containing every stored book
     */
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Validates that a text argument is present and not blank.
     *
     * @param value the text value to validate
     * @param fieldName the logical name of the field being validated
     * @throws IllegalArgumentException if the value is null or blank
     */
    private void validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }
}
