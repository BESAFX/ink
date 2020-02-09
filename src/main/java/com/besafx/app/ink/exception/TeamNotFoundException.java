package com.besafx.app.ink.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(Long id) {
        super(String.format("Team with id %s not found.", id));
    }
}
