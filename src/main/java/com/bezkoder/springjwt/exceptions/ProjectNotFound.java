package com.bezkoder.springjwt.exceptions;

public class ProjectNotFound extends RuntimeException{
    public ProjectNotFound(Long id){
        super("Could not find the project with id "+ id);
    }
}
