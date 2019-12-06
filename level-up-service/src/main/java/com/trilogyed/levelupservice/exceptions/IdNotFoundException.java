package com.trilogyed.levelupservice.exceptions;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String message){
        super("Message from IdNotFoundException: " + message);
    }
}
