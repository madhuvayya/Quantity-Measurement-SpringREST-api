package com.quantitymeasurement.exception.handler;

import com.quantitymeasurement.exception.MeasurementServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuantityMeasurementExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleMeasurementNotFound(MeasurementServiceException measurementServiceException){
        return ResponseEntity.badRequest().body(measurementServiceException);
    }
}
