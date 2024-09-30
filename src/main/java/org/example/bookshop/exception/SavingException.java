package org.example.bookshop.exception;

public class SavingException extends RuntimeException {
    public SavingException(String message) {
        super(message);
    }

    public SavingException(String message, Throwable cause) {
        super(message, cause);
    }
}
