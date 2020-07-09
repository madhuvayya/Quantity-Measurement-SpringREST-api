package com.quantitymeasurement.model;

import com.quantitymeasurement.enums.BaseUnits;

public class Units {

    private BaseUnits firstUnit;
    private BaseUnits secondUnit;
    private double firstUnitValue;

    public Units(BaseUnits firstUnit, BaseUnits secondUnit, double firstUnitValue) {
        this.firstUnit = firstUnit;
        this.secondUnit = secondUnit;
        this.firstUnitValue = firstUnitValue;
    }

    public BaseUnits getFirstUnit() {
        return firstUnit;
    }

    public BaseUnits getSecondUnit() {
        return secondUnit;
    }

    public double getFirstUnitValue() {
        return firstUnitValue;
    }

}
