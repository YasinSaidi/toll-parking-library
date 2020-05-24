package com.yassineessaiydy.tollparkinglibrary.controller;

import com.yassineessaiydy.tollparkinglibrary.service.GenerateRandomDataService;
import com.yassineessaiydy.tollparkinglibrary.util.Mappings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Api(tags ={"Generate random data API"}, description = "API from class \"GenerateRandomDataController\" generate " +
        "random data to test the \"Toll Parking Library\" Java APIs")
@Controller
@RequestMapping(path = Mappings.GENERATE_RANDOM_DATA)
@Slf4j
public class GenerateRandomDataController {

    //fields
    private GenerateRandomDataService generateRandomDataService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss.SSS");

    //constructors
    @Autowired
    public GenerateRandomDataController(GenerateRandomDataService generateRandomDataService) {
        this.generateRandomDataService = generateRandomDataService;
    }

    //private methods

    //public methods
    /**
     *Controller to generate and save data in the application database to test "Toll Parking Library" Java APIs.
     *
     * @return A message indicating that the data insertion finished successfully.
     */
    @ApiOperation(value = "Generate and save data in the application database to be able to " +
            "test \"Toll Parking Library\" Java APIs.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "It returns a message confirming that the random data is generated " +
                    "and added to the database.", response = String.class, responseContainer = "Message")
    })
    @ResponseBody
    @GetMapping(value = Mappings.PRICING_POLICIES, produces = "application/json")
    public String generateRandomPricingPolicies() {
        log.info("> START running task: Generating and adding random data to the database.");
        String startDate = dateFormat.format(new Date());

        generateRandomDataService.generateRandomPricingPolicies();

        String endDate = dateFormat.format(new Date());
        log.info("> END running task: Generating and adding random data to the database: Start date is: {}," +
                " End date is: {}.", startDate, endDate);

        return "Generated and added random data to the database for testing purposes.";

    }

}