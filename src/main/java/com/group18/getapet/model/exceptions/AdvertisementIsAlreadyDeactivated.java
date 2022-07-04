package com.group18.getapet.model.exceptions;

public class AdvertisementIsAlreadyDeactivated extends RuntimeException {
    public AdvertisementIsAlreadyDeactivated() {
        super("Advertisement is already deactivated.");
    }
}
