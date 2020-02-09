package com.besafx.app.ink.exception;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
