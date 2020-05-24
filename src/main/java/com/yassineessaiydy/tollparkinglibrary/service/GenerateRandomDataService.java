package com.yassineessaiydy.tollparkinglibrary.service;

import com.yassineessaiydy.tollparkinglibrary.model.PricingPolicy;

import java.util.List;
import java.util.Set;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

public interface GenerateRandomDataService {

    List<String> PARKING_SLOT_TYPES = List.of("Gasoline-powered", "20-Kw-power-supply", "50-Kw-power-supply");
    List<String> CITIES = List.of("Nice", "Paris", "Bordeaux", "Nantes", "Virginia", "Maryland", "Fez");

    Set<PricingPolicy> generateRandomPricingPolicies();

}