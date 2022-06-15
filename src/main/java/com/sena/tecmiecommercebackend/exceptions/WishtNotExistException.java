package com.sena.tecmiecommercebackend.exceptions;

public class WishtNotExistException extends IllegalArgumentException {
    public WishtNotExistException(String message) {
        super(message);
    }
}