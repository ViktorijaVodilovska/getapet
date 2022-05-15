package com.group18.getapet.model.exceptions;

public class AdvertisementNotFoundException extends RuntimeException {
    public AdvertisementNotFoundException(Long id) {
        super(String.format("Advertisement with id %d was not found", id));
    }
}