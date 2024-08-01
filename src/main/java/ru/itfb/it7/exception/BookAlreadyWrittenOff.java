package ru.itfb.it7.exception;

public class BookAlreadyWrittenOff extends RuntimeException {
    public BookAlreadyWrittenOff() {
        super();
    }

    public BookAlreadyWrittenOff(String message) {
        super(message);
    }

    public BookAlreadyWrittenOff(String message, Throwable cause) {
        super(message, cause);
    }

    public BookAlreadyWrittenOff(Throwable cause) {
        super(cause);
    }

    protected BookAlreadyWrittenOff(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
