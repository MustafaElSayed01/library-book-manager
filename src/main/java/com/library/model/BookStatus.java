package com.library.model;

/**
 * Enumerates the availability states a book can have in the catalog.
 *
 * <p>The values in this enum should support the borrow and return workflows.</p>
 */
public enum BookStatus {
    AVAILABLE,
    BORROWED;
}
