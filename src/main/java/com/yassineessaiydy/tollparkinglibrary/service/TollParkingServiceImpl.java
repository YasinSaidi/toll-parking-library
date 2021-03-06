package com.yassineessaiydy.tollparkinglibrary.service;

import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlot;
import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlotType;
import com.yassineessaiydy.tollparkinglibrary.model.PricingPolicy;
import com.yassineessaiydy.tollparkinglibrary.model.TollParking;
import com.yassineessaiydy.tollparkinglibrary.repository.ParkingSlotRepository;
import com.yassineessaiydy.tollparkinglibrary.repository.ParkingSlotTypeRepository;
import com.yassineessaiydy.tollparkinglibrary.repository.TollParkingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Service
@Slf4j
public class TollParkingServiceImpl implements TollParkingService {

    //fields
    private TollParkingRepository tollParkingRepository;
    private ParkingSlotTypeRepository parkingSlotTypeRepository;
    private ParkingSlotRepository parkingSlotRepository;

    //constructors
    @Autowired
    public TollParkingServiceImpl(TollParkingRepository tollParkingRepository,
                                  ParkingSlotTypeRepository parkingSlotTypeRepository,
                                  ParkingSlotRepository parkingSlotRepository) {

        this.tollParkingRepository = tollParkingRepository;
        this.parkingSlotTypeRepository = parkingSlotTypeRepository;
        this.parkingSlotRepository = parkingSlotRepository;

    }

    public TollParkingServiceImpl() {
    }

    //private methods
    /**
     * Based on the car type, it returns the parking slot code.
     *
     * @param carType
     * @return {String}
     */
    private String parkingSlotCode(String carType) {
        HashMap<String, String> carTypeCodeMap = new HashMap<>();

        carTypeCodeMap.put(GenerateRandomDataService.PARKING_SLOT_TYPES.get(0), "G");
        carTypeCodeMap.put(GenerateRandomDataService.PARKING_SLOT_TYPES.get(1), "E20");
        carTypeCodeMap.put(GenerateRandomDataService.PARKING_SLOT_TYPES.get(2), "E50");

        return carTypeCodeMap.get(carType);
    }

    /**
     * Based on the parking pricing policy, it calculates and returns the amount
     * of money the customer should pay before leaving the parking.
     *
     * @param numberHours
     * @param fixedAmount
     * @param hourPrice
     * @return {double}
     */
    private double AmountPayPricingPolicy(int numberHours, double fixedAmount, double hourPrice) {
        if (fixedAmount != 0) {
            return fixedAmount + numberHours * hourPrice;
        } else {
            return numberHours * hourPrice;
        }
    }

    /**
     * A method that determines the amount of money the customer should pay before leaving the parking.
     *
     * @param tollParking
     * @param numberHours
     * @return {double}
     */
    private double billCustomer(TollParking tollParking, int numberHours) {
        PricingPolicy pricingPolicy = tollParking.getPricingPolicy();

        return AmountPayPricingPolicy(numberHours, pricingPolicy.getFixedAmount(), pricingPolicy.getHourPrice());
    }

    //public methods
    /**
     * A method that determines if the parking name is valid or not.
     * A valid parking name should be present in the application database.
     *
     * @param parkingName
     * @return {true} or {false}
     */
    @Override
    public boolean isParkingNameValid(String parkingName) {
        return tollParkingRepository.findByParkingName(parkingName) != null;
    }

    /**
     * A method that determines if the car type is valid or not.
     * valid car types are: "Gasoline-powered", "20-Kw-power-supply" and "50-Kw-power-supply".
     *
     * @param carType
     * @return {true} or {false}
     */
    @Override
    public boolean isCarTypeValid(String carType) {
        return GenerateRandomDataService.PARKING_SLOT_TYPES.indexOf(carType) >= 0;
    }

    /**
     * A method that determines if the slot identifier is valid or not.
     * Examples of valid slot identifiers: G-20, E20-40, E50-60
     *
     * @param carType
     * @param slotIdentifier
     * @return {true} or {false}
     */
    @Override
    public boolean isSlotNumberValid(String carType, String slotIdentifier) {
        if (slotIdentifier != null && !slotIdentifier.isEmpty()) {
            String[] stringArray = slotIdentifier.split("-");
            if(stringArray[0].equals(parkingSlotCode(carType)) && stringArray[1].matches("[1-9]\\d*")){
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * A method to determine if a slot of the right type is available and send the car to it then mark
     * it as unavailable, or refuse the car if there is no slot of the correct type available.
     *
     * @param parkingName
     * @param carType
     * @return A "String" contains the parking name and the slot number, or there is no unoccupied parking slot.
     */
    @Override
    public String parkCarInGarage(String parkingName, String carType) {
        TollParking isParkingExist = tollParkingRepository.findByParkingName(parkingName);
        if (isParkingExist != null) {
            int index = GenerateRandomDataService.PARKING_SLOT_TYPES.indexOf(carType);
            if (index >= 0) {
                ParkingSlotType parkingSlotType = parkingSlotTypeRepository.findParkingSlotType(isParkingExist.getId(),
                        carType);
                if (parkingSlotType != null) {
                    List<ParkingSlot> parkingSlotList = parkingSlotRepository.findParkingSlots(parkingSlotType.getId());
                    for (int i = 0; i < parkingSlotList.size(); i++) {
                        ParkingSlot parkingSlot = parkingSlotList.get(i);
                        if (parkingSlot.isFree()) {
                            parkingSlot.setFree(false);
                            parkingSlotRepository.save(parkingSlot);
                            return "Parking name: " + parkingName + ", Slot number: " + parkingSlotCode(carType) + "-" +
                                    parkingSlotList.get(i).getSlotNumber();
                        }
                    }
                }
            }
        }
        return "Sorry, there is no unoccupied parking slot at the moment.";
    }

    /**
     * A method determines the amount of money the customer should pay before leaving the parking.
     *
     * @param parkingName
     * @param carType
     * @param slotIdentifier
     * @param numberHours
     * @return A "String" contains the amount of money the customer should pay before leaving the parking.
     */
    @Override
    public String carLeavesGarage(String parkingName, String carType, String slotIdentifier, int numberHours) {

        TollParking isParkingExist = tollParkingRepository.findByParkingName(parkingName);
        if (isParkingExist != null && isSlotNumberValid(carType, slotIdentifier)) {
            int slotNumber = Integer.parseInt(slotIdentifier.split("-")[1]);
            int index = GenerateRandomDataService.PARKING_SLOT_TYPES.indexOf(carType);
            if (index >= 0) {
                ParkingSlotType parkingSlotType = parkingSlotTypeRepository.findParkingSlotType(isParkingExist.getId(),
                        carType);
                if (parkingSlotType != null) {
                    List<ParkingSlot> parkingSlotList = parkingSlotRepository.findParkingSlots(parkingSlotType.getId());
                    for (int i = 0; i < parkingSlotList.size(); i++) {
                        ParkingSlot parkingSlot = parkingSlotList.get(i);
                        if (parkingSlot.getSlotNumber() == slotNumber && !parkingSlot.isFree()) {
                            parkingSlot.setFree(true);
                            parkingSlotRepository.save(parkingSlot);
                            return "Could you please pay the following amount : " + billCustomer(isParkingExist, numberHours) + " EUR";
                        }
                    }
                }
            }
        }
        return null;

    }

}