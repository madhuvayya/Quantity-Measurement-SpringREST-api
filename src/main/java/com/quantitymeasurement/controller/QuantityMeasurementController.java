package com.quantitymeasurement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.enums.BaseUnits;
import com.quantitymeasurement.model.Units;
import com.quantitymeasurement.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

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

    @RequestMapping("/main-units")
    public ResponseEntity<String> getMainUnits() throws JsonProcessingException {
        String[] mainUnits = measurementService.getMainUnits();
        String jsonString = objectMapper.writeValueAsString(mainUnits);
        return new ResponseEntity<>(jsonString,HttpStatus.OK);
    }

    @GetMapping("/main-units/{main_unit}")
    public ResponseEntity<String> getSubUnits(@PathVariable String main_unit) throws JsonProcessingException {
        BaseUnits[] subUnits = measurementService.getSubUnits(main_unit);
        String jsonString = objectMapper.writeValueAsString(subUnits);
        return new ResponseEntity<String>(Arrays.toString(subUnits),HttpStatus.OK);
    }

    @PostMapping("/main-units/{main_unit}/convert")
    public ResponseEntity<String> convertUnit(@PathVariable String main_unit,@RequestBody Units units) throws JsonProcessingException {
        double convertedValue = measurementService.convertTo(main_unit,units);
        String jsonString = objectMapper.writeValueAsString(convertedValue);
        return new ResponseEntity<String>(jsonString,HttpStatus.OK);
    }

}
