package com.pewde.messagingservice.controller;

import com.pewde.messagingservice.exception.HttpException;
import com.pewde.messagingservice.mapper.ExceptionMapper;
import com.pewde.messagingservice.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Hidden
@ControllerAdvice
public class ExceptionController {

    @Autowired
    private ExceptionMapper exceptionMapper;

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ExceptionResponse> handleException(HttpException exception) {
        ExceptionResponse response = exceptionMapper.mapExceptionToExceptionResponse(exception);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
