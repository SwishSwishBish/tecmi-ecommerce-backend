package com.sena.tecmiecommercebackend.exceptions;

public class UpdateFailException extends IllegalArgumentException {
    public UpdateFailException(String message) {
        super(message);
    }
}