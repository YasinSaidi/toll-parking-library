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
@Table(name = "toll_parking")
@NoArgsConstructor
@Getter
@Setter
public class TollParking implements Serializable {

    //fields
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "parking_name")
    private String parkingName;

    @NotNull
    @Column(name = "total_parking_number_slots")
    private int totalParkingNumberSlots;

    @ManyToOne
    @JoinColumn(name = "FK_pricing_policy_id")
    @JsonIgnore
    private PricingPolicy pricingPolicy;

    @OneToMany(mappedBy = "tollParking", cascade = CascadeType.ALL)
    Set<ParkingSlotType> parkingSlotTypeSet = new HashSet<>();

    public TollParking(String parkingName, int totalParkingNumberSlots, PricingPolicy pricingPolicy) {
        this.parkingName = parkingName;
        this.totalParkingNumberSlots = totalParkingNumberSlots;
        this.pricingPolicy = pricingPolicy;
    }

}