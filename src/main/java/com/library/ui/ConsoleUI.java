package com.library.ui;

import com.library.model.Book;
import com.library.model.BookStatus;
import com.library.model.Genre;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

/**
 * Handles the console-based interaction flow for the library book manager.
 *
 * <p>This class should display the menu, collect user input, and delegate catalog
 * operations to {@code LibraryService}.</p>
 */
public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final LibraryService libraryService;

    public ConsoleUI(LibraryService libraryService) {
        if (libraryService == null) {
            throw new IllegalArgumentException("libraryService cannot be null");
        }
        this.libraryService = libraryService;
    }

    public void run() {
        System.out.println("Welcome to Library Console UI");

        while (true) {
            System.out.println("Pick a choice:");
            System.out.println("1. Add a new book");
            System.out.println("2. remove existing book");
            System.out.println("3. Search for a book by Title");
            System.out.println("4. Search for a book by author");
            System.out.println("5. Borrow a book");
            System.out.println("6. Return a book");
            System.out.println("7. List all books");
            System.out.println("0. Quit");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addBook();

                case "2" -> removeBook();

                case "3" -> searchByTitle();

                case "4" -> searchByAuthor();

                case "5" -> borrowBook();

                case "6" -> returnBook();

                case "7" -> viewAllBooks();

                case "0" -> {
                    System.out.println("Thanks for using Library Book Management System");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Prompts the user for book details and adds a new book to the catalog.
     *
     * <p>The method collects the title, author, ISBN, and genre from the console,
     * normalizes the title and author text, and creates a new {@code Book} with
     * an initial status of {@code AVAILABLE}. If the user enters an invalid genre
     * selection or the book data is invalid, an error message is displayed instead
     * of adding the book.</p>
     */
    public void addBook() {
        System.out.println("Enter book title:");
        String title = toTitleCase(scanner.nextLine());
        System.out.println("Enter book author:");
        String author = toTitleCase(scanner.nextLine());
        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine().trim();
        System.out.println("Pick of the available genres:");
        Genre[] genres = Genre.values();
        for (int i = 1; i <= genres.length; i++) {
            System.out.println(i + ". " + genres[i - 1]);
        }
        try {
            int genreOption = Integer.parseInt(scanner.nextLine().trim());
            if (genreOption < 1 || genreOption > genres.length) {
                System.out.println("Invalid option, pick a valid genre.");
                return;
            }
            Genre genre = genres[genreOption - 1];
            try {
                Book book = new Book(title, author, isbn, genre, BookStatus.AVAILABLE);
                libraryService.add(book);
                System.out.println("Book added successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input, enter a number");
        }
    }

    /**
     * Prompts the user for an ISBN and removes the matching book from the catalog.
     *
     * <p>The method validates that the ISBN is not blank before delegating the
     * removal to {@code LibraryService}. If the removal succeeds, a confirmation
     * message is displayed; otherwise, the service error message is printed.</p>
     */
    public void removeBook() {
        System.out.println("Enter book ISBN:");
        String isbn = scanner.nextLine().trim();
        if(isbn.isEmpty()) {
            System.out.println("Book ISBN can't be empty");
            return;
        }
        try {
            libraryService.remove(isbn);
            System.out.println("Book removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prompts the user for a title keyword and displays matching books.
     *
     * <p>The method rejects blank input, calls {@code LibraryService.searchByTitle},
     * and prints each matching book in a readable format. If no books match, the
     * user is informed and can try another search term.</p>
     */
    public void searchByTitle() {
        System.out.println("Enter book title or any word of it:");
        String title = scanner.nextLine().trim();
        if(title.isEmpty()) {
            System.out.println("Book title can't be empty");
            return;
        }
        List<Book> books = libraryService.searchByTitle(title);
        if (books.isEmpty()) {
            System.out.println("No books found.\n please try another words.");
            return;
        }
        System.out.println("List of found books:");
        int i = 1;
        for (Book book : books) {
            System.out.println(i + " " + book.toString());
            i++;
        }
    }

    /**
     * Prompts the user for an author name and displays matching books.
     *
     * <p>The method rejects blank input, calls {@code LibraryService.searchByAuthor},
     * and prints each matching book in a readable format. If no books match, the
     * user is informed and can try another search term.</p>
     */
    public void searchByAuthor() {
        System.out.println("Enter book author name:");
        String authorName = scanner.nextLine().trim();
        if(authorName.isEmpty()) {
            System.out.println("Book author name can't be empty");
            return;
        }
        List<Book> books = libraryService.searchByAuthor(authorName);
        if (books.isEmpty()) {
            System.out.println("No books found.\n please try another word.");
            return;
        }
        System.out.println("List of found books:");
        int i = 1;
        for (Book book : books) {
            System.out.println(i + " " + book.toString());
            i++;
        }
    }

    /**
     * Prompts the user for an ISBN and marks the matching book as borrowed.
     *
     * <p>This method will eventually validate the input, call
     * {@code LibraryService.borrowBook}, and report any errors for blank ISBNs,
     * missing books, or books that are already borrowed.</p>
     */
    public void borrowBook() {
        // TODO: Prompt the user for the ISBN of the book to borrow.
        // TODO: Call libraryService.borrowBook(isbn).
        // TODO: Handle errors for blank ISBN, missing book, or already borrowed book.
        // TODO: Print a success message when borrowing succeeds.
    }

    /**
     * Prompts the user for an ISBN and marks the matching book as available.
     *
     * <p>This method will eventually validate the input, call
     * {@code LibraryService.returnBook}, and report any errors for blank ISBNs,
     * missing books, or books that are already available.</p>
     */
    public void returnBook() {
        // TODO: Prompt the user for the ISBN of the book to return.
        // TODO: Call libraryService.returnBook(isbn).
        // TODO: Handle errors for blank ISBN, missing book, or already returned book.
        // TODO: Print a success message when returning succeeds.
    }

    /**
     * Displays every book currently stored in the catalog.
     *
     * <p>This method will eventually fetch the full list from
     * {@code LibraryService}, handle the empty-catalog case, and print each book's
     * title, author, ISBN, genre, and status in a readable format.</p>
     */
    public void viewAllBooks() {
        // TODO: Fetch all books with libraryService.getBooks().
        // TODO: If the catalog is empty, print a message saying there are no books yet.
        // TODO: Otherwise, loop through the list and print each book's title, author,
        // TODO: ISBN, genre, and availability status.
    }

    static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
            }
        }

        return result.toString().trim();
    }
}
