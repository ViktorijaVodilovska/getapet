package com.group18.getapet.model.exceptions;

public class NullPetException extends RuntimeException {

    public NullPetException() {
        super("Pet field must not be null.");
    }
}