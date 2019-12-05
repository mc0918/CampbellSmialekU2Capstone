package com.trilogyed.invoiceservice.exceptions;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String message){
        super("Message from IdNotFoundException: " + message);
    }
}
