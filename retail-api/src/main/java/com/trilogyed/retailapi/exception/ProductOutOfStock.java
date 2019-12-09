package com.trilogyed.retailapi.exception;

public class ProductOutOfStock extends RuntimeException {
    public ProductOutOfStock(String message){
        super("Message from ProductOutOfStockException: " + message);
    }
}
