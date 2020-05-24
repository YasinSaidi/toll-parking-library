package com.yassineessaiydy.tollparkinglibrary.repository;

import com.yassineessaiydy.tollparkinglibrary.model.ParkingSlotType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Repository
public interface ParkingSlotTypeRepository extends CrudRepository<ParkingSlotType, Long> {

    @Query("from ParkingSlotType parkingSlotType where parkingSlotType" +
            ".type = :parkingSlotType and parkingSlotType.tollParking.id = :parkingId")
    ParkingSlotType findParkingSlotType(@Param("parkingId") Long parkingId, @Param("parkingSlotType") String parkingSlotType);

}