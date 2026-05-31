package com.library;

import com.library.service.LibraryService;
import com.library.ui.ConsoleUI;

/**
 * Application entry point for the library book manager.
 *
 * <p>This class is expected to bootstrap the console user interface and wire the
 * service layer to the interactive menu flow.</p>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Library Book Manager...");

        LibraryService libraryService = new LibraryService();
        ConsoleUI consoleUI = new ConsoleUI(libraryService);
        try {
            consoleUI.run();
        } finally {
            System.out.println("Library Book Manager closed.");
        }
    }
}
