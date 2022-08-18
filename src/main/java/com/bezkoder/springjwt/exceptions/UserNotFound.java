package com.bezkoder.springjwt.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(Long id){
        super("Could not find the user with id "+ id);
    }
}
