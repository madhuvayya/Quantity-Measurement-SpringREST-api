package com.quantitymeasurement.enums;

public enum BaseUnits {

    // Length units
    // Inch is the base unit
    FEET(12.0),
    YARD(36.0),
    INCH(1.0),
    CENTIMETRE(0.393701),
    KM(39370.1),

    // Volume is the unit type
    // Litre is the base unit
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78),

    // Weight is the unit type
    // Kg is the base unit
    KG(1.0),
    GRAMS(0.001),
    TONNE(1000.0),

    // Temperature is the unit type
    // Fahrenheit is the base unit
    FAHRENHEIT(1.0),
    CELSIUS(33.8),
    KELVIN(-457.87);

    public double value;

    BaseUnits(double value) {
        this.value = value;
    }
}
