package com.seeit.holycode.controller;

import com.seeit.holycode.exception.PlacesException;
import com.seeit.holycode.model.ErrorResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(PlacesException.class)
    public ResponseEntity<Object> exceptionHandler(PlacesException e) {
        return new ResponseEntity<Object>(
                ErrorResponse
                        .builder()
                        .code(e.getStatusCode())
                        .reason(e.getMessage())
                        .build(),
                new HttpHeaders(),
                e.getStatusCode());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> exceptionHandler(BadCredentialsException e) {
        log.error("Authentication error", e);
        int code = HttpStatus.UNAUTHORIZED.value();
        return new ResponseEntity<Object>(
                ErrorResponse
                        .builder()
                        .code(code)
                        .reason(e.getMessage())
                        .build(),
                new HttpHeaders(),
                code);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception e) {
        log.error("ERROR", e);
        return new ResponseEntity<Object>(
                ErrorResponse
                        .builder()
                        .code(500)
                        .reason(e.getMessage())
                        .build(),
                new HttpHeaders(),
                500);
    }
}
