package com.quantitymeasurement.service;

import com.quantitymeasurement.enums.BaseUnits;
import com.quantitymeasurement.exception.MeasurementServiceException;
import com.quantitymeasurement.model.Units;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.quantitymeasurement.enums.BaseUnits.*;
import static java.lang.Math.round;

@Service
public class MeasurementService {

    Map<String, BaseUnits[]> mainUnits;

    BaseUnits[] length = {FEET,INCH,KM,CENTIMETRE,YARD};
    BaseUnits[] volume = {LITRE,GALLON,MILLILITRE};
    BaseUnits[] weight = {KG,GRAMS,TONNE};
    BaseUnits[] temperature = {FAHRENHEIT,CELSIUS,KELVIN};

    public MeasurementService() {
        mainUnits = new HashMap<>();
        mainUnits.put("length",length);
        mainUnits.put("weight",weight);
        mainUnits.put("volume",volume);
        mainUnits.put("temperature",temperature);
    }

    public String getMainUnits() {
        return null;
    }

    public String getSubUnits(String main_unit) {
        return null;
    }

    public double convertTo(String mainUnit, Units units) {
        BaseUnits firstUnit = units.getFirstUnit();
        BaseUnits secondUnit = units.getSecondUnit();
        double firstUnitValue = units.getFirstUnitValue();

        this.checkSameType(mainUnit,firstUnit,secondUnit);
        if(mainUnit.toLowerCase().equals("temperature")){
             return round(this.temperatureConversion(firstUnit, secondUnit, firstUnitValue) * 1000.0) / 1000.0;
        }

        if(firstUnit.equals(BaseUnits.INCH))
            return round(firstUnitValue * firstUnit.value * 100.0) / 100.0;
        return round(firstUnitValue * firstUnit.value / secondUnit.value * 100.0) / 100.0;
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
        BaseUnits[] baseUnits = mainUnits.get(measurement.toLowerCase());

        if(baseUnits == null)
            throw new MeasurementServiceException(MeasurementServiceException.ExceptionType.NOT_FOUND,
                    measurement+" is not found in the measurements");

        long count1 = Arrays.stream(baseUnits).filter(units -> units.equals(firstUnit)).count();
        long count2 = Arrays.stream(baseUnits).filter(units -> units.equals(secondUnit)).count();

        if(count1 != 1 || count2 != 1)
            throw new MeasurementServiceException(MeasurementServiceException.ExceptionType.INCOMPATIBLE_UNITS
                    ,firstUnit+" , "+ secondUnit +" are incompatible unit types");
    }
}
