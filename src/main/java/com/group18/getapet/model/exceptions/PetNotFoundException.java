package com.group18.getapet.model.exceptions;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long id) {
        super(String.format("Pet with id %d was not found",id));
    }
}
