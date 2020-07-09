package com.quantitymeasurement.exception;

import org.springframework.http.HttpStatus;

public class MeasurementServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public MeasurementServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
