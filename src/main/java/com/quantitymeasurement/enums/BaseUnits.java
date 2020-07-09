package com.quantitymeasurement.enums;

public enum BaseUnits {
    INCH(1.0),
    FEET(12.0);

    private final double value;

    BaseUnits(double value) {
        this.value = value;
    }

    public double getConversionValue(){
        return value;
    }
}
