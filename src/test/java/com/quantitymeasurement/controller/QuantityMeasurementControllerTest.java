package com.quantitymeasurement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.model.Units;
import com.quantitymeasurement.service.MeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.quantitymeasurement.enums.BaseUnits.FEET;
import static com.quantitymeasurement.enums.BaseUnits.INCH;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuantityMeasurementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MeasurementService measurementService;

    @Autowired
    private ObjectMapper objectMapper;

    enum MainUnits {
        LENGTH, VOLUME, WEIGHT, TEMPERATURE
    }

    enum Length{
        INCH,FEET,CENTIMETRE,YARD,KM
    }

    @Test
    void givenDefaultUrl_shouldReturnMainUnits() throws Exception {

        String mainUnits = Arrays.toString(MainUnits.values());

        when(measurementService.getMainUnits()).thenReturn(mainUnits);

        mockMvc.perform(get("/quantity-measurement/main-units"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[0]",is("LENGTH")))
                .andExpect(jsonPath("$[1]",is("VOLUME")))
                .andExpect(jsonPath("$[2]",is("WEIGHT")))
                .andExpect(jsonPath("$[3]",is("TEMPERATURE")));

        verify(measurementService, times(1)).getMainUnits();
    }

    @Test
    void givenMainUnitInPathVariable_shouldReturnSubUnits() throws Exception {

        String subUnits = Arrays.toString(Length.values());

        when(measurementService.getSubUnits(any())).thenReturn(subUnits);

        mockMvc.perform(get("/quantity-measurement/main-units/LENGTH"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(5)))
                .andExpect(jsonPath("$[0]",is("INCH")))
                .andExpect(jsonPath("$[1]",is("FEET")))
                .andExpect(jsonPath("$[2]",is("CENTIMETRE")))
                .andExpect(jsonPath("$[3]",is("YARD")))
                .andExpect(jsonPath("$[4]",is("KM")));

        verify(measurementService, times(1)).getSubUnits(any());
    }

    @Test
    void givenUnits_whenConverted_shouldReturnSubUnits() throws Exception {

        Units units = new Units(FEET,INCH,1.0);

        String asString = objectMapper.writeValueAsString(units);

        when(measurementService.convertTo(any(),any())).thenReturn(3.0);

        mockMvc.perform(post("/quantity-measurement/main-units/LENGTH/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(3.0)));

        verify(measurementService, times(1)).convertTo(any(),any());
    }
}
