package com.yassineessaiydy.tollparkinglibrary.service;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

public interface TollParkingService {

    boolean isParkingNameValid(String parkingName);

    boolean isCarTypeValid(String carType);

    boolean isSlotNumberValid(String carType, String slotIdentifier);

    String parkCarInGarage(String parkingName, String carType);

    String carLeavesGarage(String parkingName, String carType, String slotIdentifier, int numberHours);

}