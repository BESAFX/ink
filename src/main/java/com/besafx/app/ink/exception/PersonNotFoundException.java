package com.besafx.app.ink.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Long id) {
        super(String.format("Person with id %s not found.", id));
    }

    public PersonNotFoundException(String email) {
        super(String.format("Person with email %s not found.", email));
    }
}
