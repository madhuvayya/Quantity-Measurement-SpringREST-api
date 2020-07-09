package com.quantitymeasurement.service;

import com.quantitymeasurement.exception.MeasurementServiceException;
import com.quantitymeasurement.model.Units;
import org.junit.jupiter.api.Test;

import static com.quantitymeasurement.enums.BaseUnits.*;
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

    @Test
    public void given2YardInchUnit_whenConverted_ShouldReturn72() {
        Units units = new Units(YARD,INCH,2.0);
        double convertedValue = measurementService.convertTo("LENGTH", units);
        assertEquals(72.0,convertedValue,0.0);
    }

    @Test
    public void given3FeetYardUnit_whenConvertedToYard_ShouldReturn1() {
        Units units = new Units(FEET,YARD,3.0);
        double convertedValue = measurementService.convertTo("LENGTH", units);
        assertEquals(1.0,convertedValue,0.0);
    }

    @Test
    public void given1KmYardUnit_whenConvertedToYard_ShouldReturn1() {
        Units units = new Units(KM,YARD,1.0);
        double convertedValue = measurementService.convertTo("LENGTH", units);
        assertEquals(1093.61,convertedValue,0.0);
    }


    @Test
    public void given1CentimetreAndInchUnit_whenConvertedToInch_ShouldReturn1() {
        Units units = new Units(CENTIMETRE,INCH,1.0);
        double convertedValue = measurementService.convertTo("LENGTH", units);
        assertNotEquals(1,convertedValue,0.0);
    }

    @Test
    public void given1LitreAndMillilitreUnit_whenConvertedToMillilitre_ShouldReturn1000() {
        Units units = new Units(LITRE,MILLILITRE,1.0);
        double convertedValue = measurementService.convertTo("VOLUME", units);
        assertEquals(1000,convertedValue,0.0);
    }

    @Test
    public void given1GallonAndLitreUnit_whenConvertedToLitre_ShouldReturnCorrectValue() {
        Units units = new Units(GALLON,LITRE,1.0);
        double convertedValue = measurementService.convertTo("VOLUME", units);
        assertNotEquals(10,convertedValue,0.0);
    }

    @Test
    public void given500MillilitreAndLitreUnit_whenConvertedToLitre_ShouldReturnHalfLiter() {
        Units units = new Units(MILLILITRE,LITRE,500.0);
        double convertedValue = measurementService.convertTo("VOLUME", units);
        assertEquals(0.5,convertedValue,0.0);
    }

    @Test
    public void given1KgAndGramUnit_whenConvertedToGram_ShouldReturn1000() {
        Units units = new Units(KG,GRAMS,1.0);
        double convertedValue = measurementService.convertTo("WEIGHT", units);
        assertEquals(1000,convertedValue,0.0);
    }

    @Test
    public void given1KgAndLitreUnit_whenDifferentTypeUnits_ShouldThroughException() {
        Units units = new Units(KG,LITRE,1.0);
        try {
            double convertedValue = measurementService.convertTo("WEIGHT", units);
        } catch (MeasurementServiceException e) {
            assertEquals(MeasurementServiceException.ExceptionType.INCOMPATIBLE_UNITS,e.type);
        }
    }
}
