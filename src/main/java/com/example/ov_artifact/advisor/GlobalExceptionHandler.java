package com.example.ov_artifact.advisor;

import com.example.ov_artifact.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleAllExceptions(Exception e) {
        return new ResponseEntity<>(
                new StandardResponse(false, 500, "Something went wrong: " + e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}