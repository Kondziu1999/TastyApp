package com.kondziu.projects.TastyAppBackend.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class AuthControllerHandler {

    @ExceptionHandler({InvalidConfirmationTokenException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request){
        HttpHeaders httpHeaders=new HttpHeaders();

        if(ex instanceof InvalidConfirmationTokenException){
            HttpStatus status = HttpStatus.BAD_REQUEST;
            InvalidConfirmationTokenException exception = (InvalidConfirmationTokenException) ex;

            return handleInvalidConfirmationTokenException(exception,httpHeaders,status,request);
        }
        else{
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, httpHeaders, status, request);
        }
    }

    protected ResponseEntity<ApiError> handleInvalidConfirmationTokenException(
            InvalidConfirmationTokenException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<String> errors=Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex,new ApiError(errors),headers,status,request);
    }


    //single method to customize response type of all exceptions
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
