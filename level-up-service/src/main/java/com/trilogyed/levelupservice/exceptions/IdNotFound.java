package com.trilogyed.levelupservice.exceptions;

public class IdNotFound extends RuntimeException {
    public IdNotFound(String message){
        super("Message from IdNotFoundException: " + message);
    }
}
