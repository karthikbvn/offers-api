package com.fintech.api.validation;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ValidationException extends ResponseStatusException {

    public ValidationException(String message) {
        super(BAD_REQUEST, message);
    }

    public ValidationException() {
        super(BAD_REQUEST);
    }
}
