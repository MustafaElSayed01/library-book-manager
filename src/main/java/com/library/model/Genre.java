package com.library.model;

/**
 * Enumerates the supported genres for books stored in the library catalog.
 *
 * <p>This enum can be used to keep genre input consistent when books are added
 * through the console menu.</p>
 */
public enum Genre {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE("Science"),
    HISTORY("History"),
    BIOGRAPHY("Biography"),
    TECHNOLOGY("Technology"),
    OTHER("Other");

    private final String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
