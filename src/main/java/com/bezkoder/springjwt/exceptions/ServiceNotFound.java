package com.bezkoder.springjwt.exceptions;

public class ServiceNotFound extends RuntimeException{
    public ServiceNotFound(Long id){
        super("Could not find the cervice with id "+ id);
    }
}
