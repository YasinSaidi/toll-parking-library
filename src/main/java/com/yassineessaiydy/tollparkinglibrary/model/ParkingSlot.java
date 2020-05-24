package com.yassineessaiydy.tollparkinglibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Entity
@Table(name = "parking_slot")
@NoArgsConstructor
@Getter
@Setter
public class ParkingSlot implements Serializable {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "is_free")
    boolean isFree;

    @NotNull
    @Column(name = "slot_number")
    int slotNumber;

    @ManyToOne
    @JoinColumn(name = "FK_parking_slot_type_id")
    @JsonIgnore
    private ParkingSlotType parkingSlotType;

    public ParkingSlot(boolean isFree, int slotNumber) {
        this.isFree = isFree;
        this.slotNumber = slotNumber;
    }

}