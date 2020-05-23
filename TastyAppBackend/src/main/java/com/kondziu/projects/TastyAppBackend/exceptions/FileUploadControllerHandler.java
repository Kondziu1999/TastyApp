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
public class FileUploadControllerHandler {

    @ExceptionHandler({RecipeNotFoundException.class,ImageNotFoundException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (ex instanceof RecipeNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            RecipeNotFoundException exception = (RecipeNotFoundException) ex;

            return handleRecipeNotFoundException(exception,httpHeaders,status,request);
        }
        else if(ex instanceof ImageNotFoundException){
            HttpStatus status = HttpStatus.NOT_FOUND;
            ImageNotFoundException exception = (ImageNotFoundException) ex;

            return handleImageNotFoundException(exception,httpHeaders,status,request);
        }
        else {
            HttpStatus status=HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null,httpHeaders,status,request);
        }
    }

    protected ResponseEntity<ApiError> handleRecipeNotFoundException(
            RecipeNotFoundException ex,HttpHeaders headers, HttpStatus status, WebRequest request){
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex,new ApiError(errors),headers,status,request);
    }
    protected ResponseEntity<ApiError> handleImageNotFoundException(
            ImageNotFoundException ex,HttpHeaders headers, HttpStatus status, WebRequest request){

        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors),headers,status,request);
    }
    //single method to customize response type of all exceptions
    protected ResponseEntity<ApiError> handleExceptionInternal(
            Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
