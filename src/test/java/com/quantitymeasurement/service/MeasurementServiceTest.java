package com.quantitymeasurement.service;

import com.quantitymeasurement.model.Units;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.quantitymeasurement.enums.BaseUnits.FEET;
import static com.quantitymeasurement.enums.BaseUnits.INCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MeasurementServiceTest {

    MeasurementService measurementService = new MeasurementService();

    @Test
    public void given1FeetInch_whenConverted_ShouldReturn12() {
        Units units = new Units(FEET,INCH,1.0);
        double convertedValue = measurementService.convertTo("LENGTH", units);
        assertEquals(12.0,convertedValue,0.0);
    }

    @Test
    public void given1FeetInch_whenConverted_ShouldReturnCorrectValue() {
        Units units = new Units(FEET,INCH,1.0);
        double convertedValue = measurementService.convertTo("LENGTH", units);
        assertNotEquals(23.0,convertedValue,0.0);
    }
}
