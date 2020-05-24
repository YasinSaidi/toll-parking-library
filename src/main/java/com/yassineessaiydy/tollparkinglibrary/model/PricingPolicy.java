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
@Table(name = "pricing_policy")
@NoArgsConstructor
@Getter
@Setter
public class PricingPolicy implements Serializable {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "pricing_policy_name")
    private String PricingPolicyName;

    @NotNull
    @Column(name = "hour_price")
    private double hourPrice;

    @Column(name = "fixed_amount")
    private double fixedAmount;

    @OneToMany(mappedBy = "pricingPolicy", cascade = CascadeType.ALL)
    Set<TollParking> parkingSet = new HashSet<>();

    public PricingPolicy(String pricingPolicyName, double hourPrice, double fixedAmount) {
        PricingPolicyName = pricingPolicyName;
        this.hourPrice = hourPrice;
        this.fixedAmount = fixedAmount;
    }

}