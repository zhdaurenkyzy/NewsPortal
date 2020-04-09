package com.epam.news.exception;

public class CustomGeneralException extends RuntimeException {

    public CustomGeneralException() {
    }

    public CustomGeneralException(String message) {
        super(message);
    }

    public CustomGeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}
