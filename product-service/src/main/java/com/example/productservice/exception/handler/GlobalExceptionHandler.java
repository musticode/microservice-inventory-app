package com.example.productservice.exception.handler;

import com.example.productservice.dto.ExceptionResponse;
import com.example.productservice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> productNotFoundExceptionHandler(ProductNotFoundException exception){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .code(404)
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
