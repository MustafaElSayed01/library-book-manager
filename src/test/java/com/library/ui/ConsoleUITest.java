package com.library.ui;

import com.library.model.Book;
import com.library.model.BookStatus;
import com.library.model.Genre;
import com.library.service.LibraryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleUITest {

    private java.io.InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUpStreams() {
        originalIn = System.in;
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void addBookShouldAddBookThroughConsoleFlow() {
        System.setIn(new ByteArrayInputStream(("Clean Code\nRobert Martin\n123\n6\n").getBytes(StandardCharsets.UTF_8)));
        LibraryService service = new LibraryService();
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.addBook();

        assertEquals(1, service.getBooks().size());
        assertTrue(outputStream.toString(StandardCharsets.UTF_8).contains("Book added successfully."));
    }

    @Test
    void removeBookShouldRemoveBookThroughConsoleFlow() {
        LibraryService service = new LibraryService();
        service.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));
        System.setIn(new ByteArrayInputStream("123\n".getBytes(StandardCharsets.UTF_8)));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.removeBook();

        assertTrue(service.getBooks().isEmpty());
        String output = normalize(outputStream.toString(StandardCharsets.UTF_8));
        assertTrue(output.contains("Enter book ISBN:"));
        assertTrue(output.contains("Book removed successfully."));
    }

    @Test
    void searchByTitleShouldPrintMatchingBook() {
        LibraryService service = new LibraryService();
        service.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));
        System.setIn(new ByteArrayInputStream("clean\n".getBytes(StandardCharsets.UTF_8)));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.searchByTitle();

        String output = outputStream.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("List of found books:"));
        assertTrue(output.contains("Clean Code"));
        assertTrue(output.contains("Robert Martin"));
    }

    @Test
    void searchByAuthorShouldPrintMatchingBook() {
        LibraryService service = new LibraryService();
        service.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));
        System.setIn(new ByteArrayInputStream("martin\n".getBytes(StandardCharsets.UTF_8)));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.searchByAuthor();

        String output = outputStream.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("List of found books:"));
        assertTrue(output.contains("Clean Code"));
        assertTrue(output.contains("Robert Martin"));
    }

    @Test
    void borrowBookShouldMarkBookBorrowedThroughConsoleFlow() {
        LibraryService service = new LibraryService();
        service.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));
        System.setIn(new ByteArrayInputStream("123\n".getBytes(StandardCharsets.UTF_8)));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.borrowBook();

        assertEquals(BookStatus.BORROWED, service.getBooks().get(0).getBookStatus());
        String output = normalize(outputStream.toString(StandardCharsets.UTF_8));
        assertTrue(output.contains("Book borrowed successfully."));
    }

    @Test
    void returnBookShouldMarkBookAvailableThroughConsoleFlow() {
        LibraryService service = new LibraryService();
        service.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.BORROWED));
        System.setIn(new ByteArrayInputStream("123\n".getBytes(StandardCharsets.UTF_8)));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.returnBook();

        assertEquals(BookStatus.AVAILABLE, service.getBooks().get(0).getBookStatus());
        String output = normalize(outputStream.toString(StandardCharsets.UTF_8));
        assertTrue(output.contains("Book returned successfully."));
    }

    @Test
    void removeBookShouldRejectBlankIsbn() {
        LibraryService service = new LibraryService();
        System.setIn(new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8)));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.removeBook();

        assertEquals(0, service.getBooks().size());
        assertTrue(normalize(outputStream.toString(StandardCharsets.UTF_8)).contains("Book ISBN can't be empty"));
    }

    @Test
    void viewAllBooksShouldReportEmptyCatalog() {
        LibraryService service = new LibraryService();
        System.setIn(new ByteArrayInputStream(new byte[0]));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.viewAllBooks();

        assertEquals("There are no books yet!", normalize(outputStream.toString(StandardCharsets.UTF_8)));
    }

    @Test
    void viewAllBooksShouldPrintCatalogEntries() {
        LibraryService service = new LibraryService();
        service.add(new Book("Clean Code", "Robert Martin", "123", Genre.TECHNOLOGY, BookStatus.AVAILABLE));
        service.add(new Book("Refactoring", "Martin Fowler", "456", Genre.TECHNOLOGY, BookStatus.BORROWED));
        System.setIn(new ByteArrayInputStream(new byte[0]));
        ConsoleUI consoleUI = new ConsoleUI(service);

        consoleUI.viewAllBooks();

        String output = normalize(outputStream.toString(StandardCharsets.UTF_8));
        assertTrue(output.contains("Title: Clean Code"));
        assertTrue(output.contains("Title: Refactoring"));
        assertTrue(output.contains("Status: AVAILABLE"));
        assertTrue(output.contains("Status: BORROWED"));
    }

    private String normalize(String value) {
        return value.replace("\r\n", "\n").trim();
    }
}
