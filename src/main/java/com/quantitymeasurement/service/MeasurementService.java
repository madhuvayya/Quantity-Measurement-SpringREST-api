package com.quantitymeasurement.service;

import com.quantitymeasurement.enums.BaseUnits;
import com.quantitymeasurement.exception.MeasurementServiceException;
import com.quantitymeasurement.model.Units;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.quantitymeasurement.enums.BaseUnits.*;
import static java.lang.Math.round;

@Service
public class MeasurementService {

    Map<String, BaseUnits[]> measurements;

    BaseUnits[] length = {FEET,INCH,KM,CENTIMETRE,YARD};
    BaseUnits[] volume = {LITRE,GALLON,MILLILITRE};
    BaseUnits[] weight = {KG,GRAMS,TONNE};
    BaseUnits[] temperature = {FAHRENHEIT,CELSIUS,KELVIN};

    public MeasurementService() {
        measurements = new HashMap<>();
        measurements.put("length",length);
        measurements.put("weight",weight);
        measurements.put("volume",volume);
        measurements.put("temperature",temperature);

    }

    public String[] getMeasurements() {
        return measurements.keySet().toArray(new String[0]);
    }

    public BaseUnits[] getSubUnits(String measurement) {
        this.checkMeasurementExists(measurement);
        return this.measurements.get(measurement.toLowerCase());
    }

    public double convertTo(String measurement, Units units) {
        BaseUnits firstUnit = units.getFirstUnit();
        BaseUnits secondUnit = units.getSecondUnit();
        double firstUnitValue = units.getFirstUnitValue();

        this.checkSameType(measurement,firstUnit,secondUnit);

        if(measurement.toLowerCase().equals("temperature")){
             return round(this.temperatureConversion(firstUnit, secondUnit, firstUnitValue) * 1000.0) / 1000.0;
        }
        if(firstUnit.equals(BaseUnits.INCH))
            return round(firstUnitValue * firstUnit.value * 1000.0 ) / 1000.0;
        return round(firstUnitValue * ( firstUnit.value / secondUnit.value) * 1000.0 ) / 1000.0 ;
    }

    private double temperatureConversion(BaseUnits firstUnit, BaseUnits secondUnit, double firstUnitValue) {
        if(firstUnit.equals(FAHRENHEIT))
            return this.convertFahrenheitToOtherUnits(firstUnitValue,secondUnit);
        if(secondUnit.equals(FAHRENHEIT))
            return this.convertOtherUnitsToFahrenheit(firstUnitValue,secondUnit);
        return this.convertOtherTemperatureUnits(firstUnit,secondUnit, firstUnitValue);
    }

    private double convertFahrenheitToOtherUnits(double firstUnitValue, BaseUnits secondUnit) {
        if(secondUnit.equals(CELSIUS))
            return (firstUnitValue -32 ) * 5 / 9 ;
        return (firstUnitValue -32 ) * 5 / 9  + 273.15;
    }

    private double convertOtherUnitsToFahrenheit(double firstUnitValue, BaseUnits secondUnit) {
        if(secondUnit.equals(CELSIUS))
            return firstUnitValue * 9/ 5 + 32;
        return ( firstUnitValue -273.15 ) * 9 / 5 + 32;
    }

    private double convertOtherTemperatureUnits(BaseUnits firstUnit, BaseUnits secondUnit, double firstUnitValue){
        if(firstUnit.equals(CELSIUS) && secondUnit.equals(KELVIN))
            return firstUnitValue + 273.15;
        return firstUnitValue - 273.15 ;
    }

    private void checkSameType(String measurement,BaseUnits firstUnit,BaseUnits secondUnit) {
        BaseUnits[] baseUnits = measurements.get(measurement.toLowerCase());

        this.checkMeasurementExists(measurement);
        long count1 = Arrays.stream(baseUnits).filter(units -> units.equals(firstUnit)).count();
        long count2 = Arrays.stream(baseUnits).filter(units -> units.equals(secondUnit)).count();

        if(count1 != 1 || count2 != 1)
            throw new MeasurementServiceException(HttpStatus.BAD_REQUEST
                    ,firstUnit+" , "+ secondUnit +" are incompatible unit types");
    }

    private void checkMeasurementExists(String measurement){
        BaseUnits[] baseUnits = measurements.get(measurement.toLowerCase());

        if(baseUnits == null)
            throw new MeasurementServiceException(HttpStatus.NOT_FOUND,
                    measurement+" is not found in the measurements");
    }
}
