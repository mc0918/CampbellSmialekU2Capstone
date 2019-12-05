package com.trilogyed.productservice.exception;

public class IdNotFound extends RuntimeException {
    public IdNotFound(String message) {
        super("Message from IdNotFound: " + message);
    }
}
