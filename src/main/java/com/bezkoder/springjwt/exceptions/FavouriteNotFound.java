package com.bezkoder.springjwt.exceptions;

public class FavouriteNotFound extends RuntimeException{
    public FavouriteNotFound(Long id){
        super("Could not find the favourite with id "+ id);
    }
}
