package com.quantitymeasurement.exception;

public class MeasurementServiceException extends RuntimeException {

    public enum ExceptionType {
        INCOMPATIBLE_UNITS,
        NOT_FOUND
    }
    public ExceptionType type;

    public MeasurementServiceException(ExceptionType type,String message) {
        super(message);
        this.type = type;
    }
}
