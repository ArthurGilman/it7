package ru.itfb.it7.exception;

public class ReaderNotExist extends RuntimeException{
    public ReaderNotExist() {
    }

    public ReaderNotExist(String message) {
        super(message);
    }

    public ReaderNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public ReaderNotExist(Throwable cause) {
        super(cause);
    }

    public ReaderNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
