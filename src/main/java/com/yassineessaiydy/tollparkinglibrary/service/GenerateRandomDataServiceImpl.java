package com.yassineessaiydy.tollparkinglibrary.service;

import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlot;
import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlotType;
import com.yassineessaiydy.tollparkinglibrary.model.PricingPolicy;
import com.yassineessaiydy.tollparkinglibrary.model.TollParking;
import com.yassineessaiydy.tollparkinglibrary.repository.PricingPolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Service
@Slf4j
public class GenerateRandomDataServiceImpl implements GenerateRandomDataService {

    //fields
    private PricingPolicyRepository pricingPolicyRepository;

    //constructors
    @Autowired
    public GenerateRandomDataServiceImpl(PricingPolicyRepository pricingPolicyRepository) {
        this.pricingPolicyRepository = pricingPolicyRepository;
    }

    //private methods
    /**
     * Generate a random set of "ParkingSlot".
     *
     * @param parkingSlotType
     * @param totalNumberSlots
     * @return {Set<ParkingSlot>}
     */
    private Set<ParkingSlot> generateRandomParkingSlotSet(ParkingSlotType parkingSlotType, int totalNumberSlots) {
        Set<ParkingSlot> parkingSlotSet = new HashSet<>();

        for (int i = 0; i < totalNumberSlots; i++) {
            ParkingSlot parkingSlot = new ParkingSlot();

            if (i < totalNumberSlots / 2) {
                parkingSlot.setFree(false);
            } else {
                parkingSlot.setFree(true);
            }

            parkingSlot.setSlotNumber(i + 1);

            parkingSlot.setParkingSlotType(parkingSlotType);

            parkingSlotSet.add(parkingSlot);
        }

        return parkingSlotSet;
    }

    /**
     * Generate a random set of "ParkingSlotType".
     *
     * @param tollParking
     * @param totalParkingNumberSlots
     * @return {Set<ParkingSlotType>}
     */
    private Set<ParkingSlotType> generateRandomParkingSlotTypeSet(TollParking tollParking, int totalParkingNumberSlots){
        Set<ParkingSlotType> parkingSlotTypeSet = new HashSet<>();

        for(int i = 0; i<3; i++){
            ParkingSlotType parkingSlotType = new ParkingSlotType();
            parkingSlotType.setType(PARKING_SLOT_TYPES.get(i));
            parkingSlotType.setTotalNumberSlots(totalParkingNumberSlots);
            parkingSlotType.setParkingSlotSet(generateRandomParkingSlotSet(parkingSlotType, totalParkingNumberSlots));
            parkingSlotType.setTollParking(tollParking);

            parkingSlotTypeSet.add(parkingSlotType);
        }

        return parkingSlotTypeSet;
    }

    /**
     * Generate a random set of "TollParking".
     *
     * @param pricingPolicy
     * @param city
     * @return {Set<TollParking>}
     */
    private Set<TollParking> generateRandomTollParkingSet(PricingPolicy pricingPolicy, String city) {
        Set<TollParking> tollParkingSet = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            TollParking tollParking = new TollParking();
            tollParking.setParkingName("Parking_" + city + "_" + (i + 1));
            tollParking.setTotalParkingNumberSlots((i + 1) * 30 * 3);
            tollParking.setParkingSlotTypeSet(generateRandomParkingSlotTypeSet(tollParking,
                    tollParking.getTotalParkingNumberSlots() / 3));
            tollParking.setPricingPolicy(pricingPolicy);

            tollParkingSet.add(tollParking);
        }

        return tollParkingSet;
    }


    //public methods
    /**
     * Generate a random set of "PricingPolicies".
     *
     * @return {Set<PricingPolicy>}
     */
    @Override
    @Transactional
    public Set<PricingPolicy> generateRandomPricingPolicies() {
        Set<PricingPolicy> pricingPolicySet = new HashSet<>();

        for (int i = 0; i < 2; i++) {

            PricingPolicy pricingPolicy = new PricingPolicy();
            pricingPolicy.setPricingPolicyName("method_" + (i + 1));
            pricingPolicy.setHourPrice(4.20 + (i + 1));
            if (i % 2 == 0) {
                pricingPolicy.setFixedAmount(3.50 + (i + 1));
            }

            if ((i >= 0) && (i < CITIES.size())) {
                pricingPolicy.setParkingSet(generateRandomTollParkingSet(pricingPolicy, CITIES.get(i)));
            } else {
                pricingPolicy.setParkingSet(generateRandomTollParkingSet(pricingPolicy, CITIES.get(0)));
            }

            pricingPolicySet.add(pricingPolicy);

        }

        pricingPolicyRepository.saveAll(pricingPolicySet);

        return pricingPolicySet;
    }

}