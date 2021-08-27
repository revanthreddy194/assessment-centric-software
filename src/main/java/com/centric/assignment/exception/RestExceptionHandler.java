package com.centric.assignment.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(BAD_REQUEST)
    protected ResponseEntity handleWeatherException(BadRequestException exception) {
        return ResponseEntity.status(BAD_REQUEST).build();
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    protected ResponseEntity handleNotFoundException(DataNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), INTERNAL_SERVER_ERROR);
    }

}
