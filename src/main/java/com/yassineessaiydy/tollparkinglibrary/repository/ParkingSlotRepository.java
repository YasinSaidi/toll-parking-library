package com.yassineessaiydy.tollparkinglibrary.repository;

import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Repository
public interface ParkingSlotRepository extends CrudRepository<ParkingSlot, Long> {

    @Query("from ParkingSlot parkingSlot where parkingSlot.parkingSlotType.id = :slotTypeId")
    List<ParkingSlot> findParkingSlots(@Param("slotTypeId") Long slotTypeId);
}