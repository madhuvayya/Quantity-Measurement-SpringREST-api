package com.quantitymeasurement.enums;

public enum BaseUnits {

    /**
     * Length units
     * Inch is the base unit
     */
    FEET(12.0),
    YARD(36.0),
    INCH(1.0),
    CENTIMETRE(0.393701),
    KM(39370.1);

    public double value;

    BaseUnits(double value) {
        this.value = value;
    }

    public double getConversionValue(){
        return value;
    }
}
