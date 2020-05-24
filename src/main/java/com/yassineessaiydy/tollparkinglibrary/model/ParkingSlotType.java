package com.yassineessaiydy.tollparkinglibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Entity
@Table(name = "parking_slot_type")
@NoArgsConstructor
@Getter
@Setter
public class ParkingSlotType implements Serializable {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "type")
    String type;

    @NotNull
    @Column(name = "total_number_slots")
    int totalNumberSlots;

    @ManyToOne
    @JoinColumn(name = "FK_toll_parking_id")
    @JsonIgnore
    private TollParking tollParking;

    @OneToMany(mappedBy = "parkingSlotType", cascade = CascadeType.ALL)
    Set<ParkingSlot> parkingSlotSet = new HashSet<>();

    public ParkingSlotType(String type, int totalNumberSlots, TollParking tollParking) {
        this.type = type;
        this.totalNumberSlots = totalNumberSlots;
        this.tollParking = tollParking;
    }

}