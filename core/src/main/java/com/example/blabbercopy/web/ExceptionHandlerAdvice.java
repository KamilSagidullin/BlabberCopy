package com.example.blabbercopy.web;

import com.example.blabbercopy.exception.BlabberException;
import com.example.blabbercopy.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(exception = BlabberException.class)
    public ResponseEntity<ErrorResponse> BlabberExceptionHandler(BlabberException ex){
        log.error("Blabber exception ", ex);
        return response(HttpStatus.BAD_REQUEST,ex.getMessage());
    }
    private ResponseEntity<ErrorResponse> response(HttpStatus status,String message){
         var errorResponseBody = ErrorResponse.builder().message(message).build();
         return  ResponseEntity.status(status).body(errorResponseBody);
    }
}
