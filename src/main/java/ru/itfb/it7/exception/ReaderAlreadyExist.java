package ru.itfb.it7.exception;

public class ReaderAlreadyExist extends RuntimeException {
    public ReaderAlreadyExist() {
    }

    public ReaderAlreadyExist(String message) {
        super(message);
    }

    public ReaderAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public ReaderAlreadyExist(Throwable cause) {
        super(cause);
    }

    public ReaderAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
