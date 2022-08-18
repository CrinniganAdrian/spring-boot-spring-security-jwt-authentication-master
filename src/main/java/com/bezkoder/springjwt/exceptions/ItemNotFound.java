package com.bezkoder.springjwt.exceptions;

public class ItemNotFound extends RuntimeException{
    public ItemNotFound(Long id){
        super("Could not find the item with id "+ id);
    }
}
