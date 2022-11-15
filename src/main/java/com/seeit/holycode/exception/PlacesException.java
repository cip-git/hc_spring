package com.seeit.holycode.exception;

import lombok.Getter;

@Getter
public class PlacesException extends RuntimeException{

    private final int statusCode;

    public PlacesException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
