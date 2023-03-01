package com.magalu.favorites.product.exception;

public class ResourceAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 2L;
    public ResourceAlreadyExistException(String msg) {
        super(msg);
    }
}
