package com.quantitymeasurement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantitymeasurement.service.MeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private String getMainUnits() {
        return Arrays.toString(MainUnits.values());
    }

    @Test
    void givenDefaultUrl_shouldReturnWelcome() throws Exception {

        when(measurementService.getMainUnits()).thenReturn(getMainUnits());

        mockMvc.perform(get("/quantity-measurement/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[0]",is("LENGTH")))
                .andExpect(jsonPath("$[1]",is("VOLUME")))
                .andExpect(jsonPath("$[2]",is("WEIGHT")))
                .andExpect(jsonPath("$[3]",is("TEMPERATURE")));

        verify(measurementService, times(1)).getMainUnits();
    }


}
