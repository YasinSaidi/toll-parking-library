package com.yassineessaiydy.tollparkinglibrary.service;

import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlot;
import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlotType;
import com.yassineessaiydy.tollparkinglibrary.model.PricingPolicy;
import com.yassineessaiydy.tollparkinglibrary.model.TollParking;
import com.yassineessaiydy.tollparkinglibrary.repository.ParkingSlotRepository;
import com.yassineessaiydy.tollparkinglibrary.repository.ParkingSlotTypeRepository;
import com.yassineessaiydy.tollparkinglibrary.repository.TollParkingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@SpringBootTest
class TollParkingServiceImplTest {

    @Autowired
    private TollParkingService tollParkingService;

    @MockBean
    private TollParkingRepository tollParkingRepository;
    @MockBean
    private ParkingSlotTypeRepository parkingSlotTypeRepository;
    @MockBean
    private ParkingSlotRepository parkingSlotRepository;

    @Test
    void isCarTypeValid() {
        TollParkingServiceImpl tollParkingService = new TollParkingServiceImpl();

        assertEquals(true, tollParkingService.isCarTypeValid("Gasoline-powered"));
        assertEquals(true, tollParkingService.isCarTypeValid("20-Kw-power-supply"));
        assertEquals(true, tollParkingService.isCarTypeValid("50-Kw-power-supply"));
        assertEquals(false, tollParkingService.isCarTypeValid("Solar-powered"));
    }

    @Test
    void isSlotNumberValid() {
        TollParkingServiceImpl tollParkingService = new TollParkingServiceImpl();

        assertEquals(true, tollParkingService.isSlotNumberValid("Gasoline-powered", "G-100"));
        assertEquals(true, tollParkingService.isSlotNumberValid("20-Kw-power-supply", "E20-200"));
        assertEquals(true, tollParkingService.isSlotNumberValid("50-Kw-power-supply", "E50-300"));
        assertEquals(false, tollParkingService.isSlotNumberValid("Gasoline-powered", "E20-400"));
        assertEquals(false, tollParkingService.isSlotNumberValid("20-Kw-power-supply", "E50-500"));
        assertEquals(false, tollParkingService.isSlotNumberValid("50-Kw-power-supply", "G-600"));
        assertEquals(false, tollParkingService.isSlotNumberValid("Solar-powered", "E20-700"));
    }

    @Test
    void parkCarInGarage() {
        String parkingName = "Parking_Nice_2";
        int totalParkingNumberSlots = 300;
        PricingPolicy pricingPolicy = new PricingPolicy();
        TollParking tollParking = new TollParking(parkingName, totalParkingNumberSlots, pricingPolicy);

        String type = "Gasoline-powered";
        int totalNumberSlots = 100;
        ParkingSlotType parkingSlotType = new ParkingSlotType(type, totalNumberSlots, tollParking);

        boolean isFree = true;
        int slotNumber = 50;
        List<ParkingSlot> parkingSlotList = new ArrayList<>();
        ParkingSlot parkingSlot = new ParkingSlot(isFree, slotNumber);
        parkingSlotList.add(parkingSlot);

        String carType = "Gasoline-powered";

        when(tollParkingRepository.findByParkingName(parkingName)).thenReturn(tollParking);
        when(parkingSlotTypeRepository.findParkingSlotType(tollParking.getId(),
                carType)).thenReturn(parkingSlotType);
        when(parkingSlotRepository.findParkingSlots(parkingSlotType.getId())).thenReturn(parkingSlotList);
        when(parkingSlotRepository.save(parkingSlot)).thenReturn(parkingSlot);

        assertEquals("Parking name: Parking_Nice_2, Slot number: G-50", tollParkingService
                .parkCarInGarage(parkingName, carType));
    }

    @Test
    void carLeavesGarage() {
        String parkingName = "Parking_Nice_2";
        int totalParkingNumberSlots = 300;
        PricingPolicy pricingPolicy = new PricingPolicy("method_1", 1.50 , 5.50);
        TollParking tollParking = new TollParking(parkingName, totalParkingNumberSlots, pricingPolicy);

        String type = "Gasoline-powered";
        int totalNumberSlots = 100;
        ParkingSlotType parkingSlotType = new ParkingSlotType(type, totalNumberSlots, tollParking);

        boolean isFree = false;
        int slotNumber = 50;
        List<ParkingSlot> parkingSlotList = new ArrayList<>();
        ParkingSlot parkingSlot = new ParkingSlot(isFree, slotNumber);
        parkingSlotList.add(parkingSlot);

        String carType = "Gasoline-powered";
        String slotIdentifier = "G-50";
        int numberHours = 5;

        when(tollParkingRepository.findByParkingName(parkingName)).thenReturn(tollParking);
        when(parkingSlotTypeRepository.findParkingSlotType(tollParking.getId(),
                carType)).thenReturn(parkingSlotType);
        when(parkingSlotRepository.findParkingSlots(parkingSlotType.getId())).thenReturn(parkingSlotList);
        when(parkingSlotRepository.save(parkingSlot)).thenReturn(parkingSlot);

        assertEquals("Could you please pay the following amount : 13.0 EUR", tollParkingService
                .carLeavesGarage(parkingName, carType, slotIdentifier, numberHours));

    }

}