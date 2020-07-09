package com.quantitymeasurement.service;

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
        return 0;
    }
}
