package com.besafx.app.ink.exception;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(Long id) {
        super(String.format("Contact with id %s not found.", id));
    }
}
