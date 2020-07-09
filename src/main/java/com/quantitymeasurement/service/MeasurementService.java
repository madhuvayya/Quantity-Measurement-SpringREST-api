package com.quantitymeasurement.service;

import com.quantitymeasurement.enums.BaseUnits;
import com.quantitymeasurement.exception.MeasurementServiceException;
import com.quantitymeasurement.model.Units;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.quantitymeasurement.enums.BaseUnits.*;

@Service
public class MeasurementService {

    Map<String, BaseUnits[]> mainUnits;

    BaseUnits[] length = {FEET,INCH,KM,CENTIMETRE,YARD};
    BaseUnits[] volume = {LITRE,GALLON,MILLILITRE};
    BaseUnits[] weight = {KG,GRAMS,TONNE};

    public MeasurementService() {
        mainUnits = new HashMap<>();
        mainUnits.put("length",length);
        mainUnits.put("weight",weight);
        mainUnits.put("volume",volume);
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

        this.checkSameType(mainUnit,firstUnit,secondUnit);

        if(firstUnit.equals(BaseUnits.INCH))
            return Math.round(units.getFirstUnitValue() * firstUnit.value * 100.0) / 100.0;
        return Math.round(units.getFirstUnitValue() * firstUnit.value / secondUnit.value * 100.0) / 100.0;
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
