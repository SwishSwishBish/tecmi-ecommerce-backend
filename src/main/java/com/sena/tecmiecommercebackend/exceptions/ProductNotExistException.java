package com.sena.tecmiecommercebackend.exceptions;

public class ProductNotExistException extends IllegalArgumentException {
    public ProductNotExistException(String message) {
        super(message);
    }
}
