package com.kondziu.projects.TastyAppBackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidConfirmationTokenException extends RuntimeException {

    public InvalidConfirmationTokenException(String message){
        super(message);
    }
    public  InvalidConfirmationTokenException(){
        super();
    }

}
