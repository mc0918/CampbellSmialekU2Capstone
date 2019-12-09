package com.trilogyed.levelupservice.exceptions;

public class IdAlreadyExists extends RuntimeException{
    public IdAlreadyExists(String message){
        super("Message from IdNotFoundException: " + message);
    }

}
