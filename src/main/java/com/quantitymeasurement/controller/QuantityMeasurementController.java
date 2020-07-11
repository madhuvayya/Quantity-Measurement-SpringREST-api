package com.quantitymeasurement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.enums.BaseUnits;
import com.quantitymeasurement.model.Units;
import com.quantitymeasurement.service.MeasurementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/quantity-measurement")
@Api
public class QuantityMeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public QuantityMeasurementController(MeasurementService measurementService, ObjectMapper objectMapper) {
        this.measurementService = measurementService;
        this.objectMapper = objectMapper;
    }

    ObjectMapper objectMapper;

    @RequestMapping("/measurements")
    @ApiOperation(value = "returns array of measurements")
    public ResponseEntity<String> getMeasurements() throws JsonProcessingException {
        String[] measurements = measurementService.getMeasurements();
        String jsonString = objectMapper.writeValueAsString(measurements);
        return new ResponseEntity<>(jsonString,HttpStatus.OK);
    }

    @GetMapping("/measurements/{measurement}")
    @ApiOperation(value = "returns array of sub units with respect to provided main unit",notes="example- /measurements/LENGTH")
    public ResponseEntity<String> getSubUnits(
            @ApiParam(value = "measurement", example = "LENGTH")
            @PathVariable String measurement) throws JsonProcessingException {
        BaseUnits[] subUnits = measurementService.getSubUnits(measurement);
        String jsonString = objectMapper.writeValueAsString(subUnits);
        return new ResponseEntity<String>(Arrays.toString(subUnits),HttpStatus.OK);
    }

    @PostMapping("/measurements/{measurement}/convert")
    @ApiOperation(value = "converts from one unit to an other unit of same type")
    public ResponseEntity<String> convertUnit(@PathVariable String measurement,@RequestBody Units units) throws JsonProcessingException {
        double convertedValue = measurementService.convertTo(measurement,units);
        String jsonString = objectMapper.writeValueAsString(convertedValue);
        return new ResponseEntity<String>(jsonString,HttpStatus.OK);
    }

}
