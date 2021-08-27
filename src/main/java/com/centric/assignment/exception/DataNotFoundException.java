package com.centric.assignment.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends RuntimeException {
    private HttpStatus errorCode;

    public DataNotFoundException(HttpStatus code) {
        this.errorCode = code;
    }
}