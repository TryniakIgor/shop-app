package com.spring.shopapp.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundExeption(ResourceNotFoundException exception, WebRequest request){
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExist.class)
    public ResponseEntity<?> handleEntityAlreadyExist(EntityAlreadyExist exception, WebRequest request){
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionDetails, HttpStatus.CONFLICT);
    }

}
