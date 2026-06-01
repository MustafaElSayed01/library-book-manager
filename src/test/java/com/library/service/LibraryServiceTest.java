package com.library.service;

import com.library.model.Book;
import com.library.model.BookStatus;
import com.library.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibraryServiceTest {

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService();
    }

    @Test
    void addShouldStoreBook() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);

        libraryService.add(book);

        List<Book> books = libraryService.getBooks();
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    void addShouldRejectNullBook() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> libraryService.add(null));

        assertEquals("book cannot be null", exception.getMessage());
    }

    @Test
    void addShouldRejectDuplicateIsbn() {
        Book firstBook = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        Book duplicateBook = new Book("Clean Architecture", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        libraryService.add(firstBook);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> libraryService.add(duplicateBook));

        assertEquals("Book with ISBN 123 already exists", exception.getMessage());
    }

    @Test
    void removeShouldDeleteBook() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        libraryService.add(book);

        libraryService.remove("123");

        assertTrue(libraryService.getBooks().isEmpty());
    }

    @Test
    void removeShouldRejectMissingBook() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> libraryService.remove("missing"));

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void searchByIsbnShouldFindMatchingBook() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        libraryService.add(book);

        Optional<Book> result = libraryService.searchByIsbn("123");

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    void searchByTitleShouldMatchCaseInsensitively() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        libraryService.add(book);

        List<Book> results = libraryService.searchByTitle("clean");

        assertEquals(1, results.size());
        assertEquals(book, results.get(0));
    }

    @Test
    void searchByAuthorShouldMatchCaseInsensitively() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        libraryService.add(book);

        List<Book> results = libraryService.searchByAuthor("martin");

        assertEquals(1, results.size());
        assertEquals(book, results.get(0));
    }

    @Test
    void borrowBookShouldChangeStatusToBorrowed() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        libraryService.add(book);

        libraryService.borrowBook("123");

        assertEquals(BookStatus.BORROWED, book.getBookStatus());
    }

    @Test
    void borrowBookShouldRejectAlreadyBorrowedBook() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.BORROWED);
        libraryService.add(book);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> libraryService.borrowBook("123"));

        assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    void returnBookShouldChangeStatusToAvailable() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.BORROWED);
        libraryService.add(book);

        libraryService.returnBook("123");

        assertEquals(BookStatus.AVAILABLE, book.getBookStatus());
    }

    @Test
    void returnBookShouldRejectAlreadyAvailableBook() {
        Book book = new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE);
        libraryService.add(book);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> libraryService.returnBook("123"));

        assertEquals("Book is already returned", exception.getMessage());
    }

    @Test
    void getBooksShouldReturnCopy() {
        libraryService.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));

        List<Book> books = libraryService.getBooks();
        books.clear();

        assertEquals(1, libraryService.getBooks().size());
    }

    @Test
    void searchMethodsShouldRejectBlankText() {
        assertThrows(IllegalArgumentException.class, () -> libraryService.searchByTitle(" "));
        assertThrows(IllegalArgumentException.class, () -> libraryService.searchByAuthor(" "));
        assertThrows(IllegalArgumentException.class, () -> libraryService.searchByIsbn(" "));
    }

    @Test
    void searchByTitleShouldReturnEmptyListWhenNoMatch() {
        libraryService.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));

        List<Book> results = libraryService.searchByTitle("does not exist");

        assertTrue(results.isEmpty());
    }

    @Test
    void searchByAuthorShouldReturnEmptyListWhenNoMatch() {
        libraryService.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));

        List<Book> results = libraryService.searchByAuthor("someone else");

        assertTrue(results.isEmpty());
    }

    @Test
    void searchByIsbnShouldReturnEmptyWhenNoMatch() {
        assertEquals(Optional.empty(), libraryService.searchByIsbn("123"));
    }
}
