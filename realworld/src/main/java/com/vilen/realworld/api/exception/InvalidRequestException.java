package com.vilen.realworld.api.exception;

import org.springframework.validation.Errors;

/**
 * Created by vilen on 17/10/24.
 */
public class InvalidRequestException extends RuntimeException {
    private final Errors errors;

    public InvalidRequestException(Errors errors) {
        super("");
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
