package com.quantitymeasurement.service;

import com.quantitymeasurement.enums.BaseUnits;
import com.quantitymeasurement.model.Units;
import org.springframework.stereotype.Service;


@Service
public class MeasurementService {

    public String getMainUnits() {
        return null;
    }

    public String getSubUnits(String main_unit) {
        return null;
    }

    public double convertTo(String mainUnit, Units units) {
        BaseUnits firstUnit = units.getFirstUnit();
        BaseUnits secondUnit = units.getSecondUnit();
        if(firstUnit.equals(BaseUnits.INCH))
            return Math.round(units.getFirstUnitValue() * firstUnit.value * 100.0) / 100.0;
        return Math.round(units.getFirstUnitValue() * firstUnit.value / secondUnit.value * 100.0) / 100.0;
    }
}
