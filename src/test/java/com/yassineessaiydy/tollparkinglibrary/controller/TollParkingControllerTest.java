package com.yassineessaiydy.tollparkinglibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class TollParkingControllerTest {

    //fields
    @Autowired
    private MockMvc mockMvc;

    //constructors

    //private methods

    //public methods
    @Test
    public void parkCarInGarage() {
        String endpoint = "/tollParking/parkCarInGarage";

        String parkingName = "Parking_Nice_2";
        String carType = "Gasoline-powered";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint)
                .param("parkingName", parkingName)
                .param("carType", carType);

        assertTimeout(Duration.ofSeconds(1), () -> {
            MvcResult mockResult = mockMvc.perform(requestBuilder).andReturn();
            assertNotNull(mockResult.getResponse().getContentAsString());
        });
    }

    @Test
    void carLeavesGarage() {
        String endpoint = "/tollParking/carLeavesGarage";

        String parkingName = "Parking_Nice_2";
        String carType = "Gasoline-powered";
        String slotNumber = "G-195";
        String numberHours = "6";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint)
                .param("parkingName", parkingName)
                .param("carType", carType)
                .param("slotNumber", slotNumber)
                .param("numberHours", numberHours);

        assertTimeout(Duration.ofSeconds(1), () -> {
            MvcResult mockResult = mockMvc.perform(requestBuilder).andReturn();
            assertNotNull(mockResult.getResponse().getContentAsString());
        });
    }

}