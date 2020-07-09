package com.quantitymeasurement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quantity-measurement")
public class QuantityMeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public QuantityMeasurementController(MeasurementService measurementService, ObjectMapper objectMapper) {
        this.measurementService = measurementService;
        this.objectMapper = objectMapper;
    }

    ObjectMapper objectMapper;

    @RequestMapping("/home")
    public ResponseEntity<String> getMainUnits() throws JsonProcessingException {
        String mainUnits = measurementService.getMainUnits();
        String s = objectMapper.writeValueAsString(mainUnits);
        return new ResponseEntity<String>(mainUnits, HttpStatus.OK);
    }


}
