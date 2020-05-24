package com.yassineessaiydy.tollparkinglibrary.repository;

import com.yassineessaiydy.tollparkinglibrary.model.PricingPolicy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Repository
public interface PricingPolicyRepository extends CrudRepository<PricingPolicy, Long> {

    PricingPolicy findById(long id);

}