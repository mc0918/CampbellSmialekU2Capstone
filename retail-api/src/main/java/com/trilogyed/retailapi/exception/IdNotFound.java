package com.trilogyed.retailapi.exception;

public class IdNotFound extends RuntimeException {
    public IdNotFound(String message){
        super("Message from IdNotFoundException: " + message);
    }
}
