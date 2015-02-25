package com.socialmap.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by yy on 2/26/15.
 */
@ControllerAdvice
// using normal @Controller cannot catch exceptions
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleRuntimeException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
