package com.besafx.app.ink.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(Long id) {
        super(String.format("Company with id %s not found.", id));
    }
}
