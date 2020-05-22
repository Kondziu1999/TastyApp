package com.kondziu.projects.TastyAppBackend.exceptions;

public class RecipeNotFoundException extends RuntimeException{

    public RecipeNotFoundException(String message){
        super(message);
    }
}
