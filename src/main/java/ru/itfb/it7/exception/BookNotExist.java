package ru.itfb.it7.exception;

public class BookNotExist extends RuntimeException {
    public BookNotExist() {
        super();
    }

    public BookNotExist(String message) {
        super(message);
    }

    public BookNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotExist(Throwable cause) {
        super(cause);
    }

    protected BookNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

