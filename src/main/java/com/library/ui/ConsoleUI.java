package com.library.ui;

import com.library.model.Book;
import com.library.model.BookStatus;
import com.library.model.Genre;
import com.library.service.LibraryService;

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


    public void removeBook() {
        // TODO: Prompt the user for the ISBN of the book to remove.
        // TODO: Call libraryService.remove(isbn).
        // TODO: Handle the case where the ISBN is blank or the bo  ok is not found.
        // TODO: Print a success message when removal succeeds.
    }

    public void searchByTitle() {
        // TODO: Prompt the user for a title keyword or full title.
        // TODO: Call libraryService.searchByTitle(title).
        // TODO: If results are empty, print that no books matched.
        // TODO: Otherwise, display each matching book in a readable format.
    }

    public void searchByAuthor() {
        // TODO: Prompt the user for an author keyword or full name.
        // TODO: Call libraryService.searchByAuthor(author).
        // TODO: If results are empty, print that no books matched.
        // TODO: Otherwise, display each matching book in a readable format.
    }

    public void borrowBook() {
        // TODO: Prompt the user for the ISBN of the book to borrow.
        // TODO: Call libraryService.borrowBook(isbn).
        // TODO: Handle errors for blank ISBN, missing book, or already borrowed book.
        // TODO: Print a success message when borrowing succeeds.
    }

    public void returnBook() {
        // TODO: Prompt the user for the ISBN of the book to return.
        // TODO: Call libraryService.returnBook(isbn).
        // TODO: Handle errors for blank ISBN, missing book, or already returned book.
        // TODO: Print a success message when returning succeeds.
    }

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
