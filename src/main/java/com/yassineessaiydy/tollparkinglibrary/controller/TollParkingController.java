package com.yassineessaiydy.tollparkinglibrary.controller;

import com.yassineessaiydy.tollparkinglibrary.domain.ErrorResponse;
import com.yassineessaiydy.tollparkinglibrary.service.TollParkingService;
import com.yassineessaiydy.tollparkinglibrary.util.Mappings;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @user    :   yessaiydy
 * @Author  :   Yassine ES-SAIYDY
 * @Date    :   21May2020
 **/

@Api(tags ={"Toll parking library Java API"}, description = "APIs from class \"TollParkingController\" to manage a" +
        " toll parking and cars of all types that it receives and leaves.")
@Controller
@RequestMapping(path = Mappings.TOLL_PARKING)
@Slf4j
public class TollParkingController {

    //fields
    @Autowired
    TollParkingService tollParkingService;

    //constructors
    @Autowired
    public TollParkingController(TollParkingService tollParkingService) {
        this.tollParkingService = tollParkingService;
    }

    //private methods

    //public methods
    /**
     *
     * @param parkingName
     * @param carType
     * @return
     */
    @ApiOperation(value = "Send the cars to the right parking slot or refuse them if there is no slot " +
            "of the right type left.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid parking name, or invalid car type.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 200, message = "It returns even the parking name and the slot number, or there is" +
                    " no unoccupied parking slot.", response = String.class, responseContainer = "Message")
    })
    @ResponseBody
    @GetMapping(value = Mappings.PARK_CAR_IN_GARAGE, produces = "application/json")
    public ResponseEntity<Object> parkCarInGarage(@ApiParam(value = "Parking name", required = true)
                                                      @RequestParam(Mappings.PARKING_NAME) String parkingName,
                                                  @ApiParam(value = "Car type: Gasoline-powered, 20 kW and 50 " +
                                                          "kW power supply electric cars", required = true)
                                                  @RequestParam(Mappings.CAR_TYPE) String carType) {

        if (!tollParkingService.isParkingNameValid(parkingName)) {

            ErrorResponse errorResponse = new ErrorResponse("Invalid parking name", System.currentTimeMillis());
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            return responseEntity;

        } else if (!tollParkingService.isCarTypeValid(carType)) {

            ErrorResponse errorResponse = new ErrorResponse("Invalid car type, we only serve " +
                    "Gasoline-powered cars, 20 kW and 50 kW power supply electric cars.", System.currentTimeMillis());
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            return responseEntity;

        } else {

            return ResponseEntity.ok().body(tollParkingService.parkCarInGarage(parkingName, carType));

        }

    }

    /**
     *
     * @param parkingName
     * @param carType
     * @param slotIdentifier
     * @param numberHours
     * @return
     */
    @ApiOperation(value = "Mark the parking slot as Free and bill the customer when the car leaves the parking.")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid parking name, invalid car type, or invalid slot number.",
                    response = ErrorResponse.class),
            @ApiResponse(code = 200, message = "It returns the amount of money the customer should pay before " +
                    "leaving the parking.", response = String.class, responseContainer = "Message")
    })
    @ResponseBody
    @GetMapping(value = Mappings.CAR_LEAVES_GARAGE, produces = "application/json")
    public ResponseEntity<Object> carLeavesGarage(@ApiParam(value = "Parking name", required = true)
                                                      @RequestParam(Mappings.PARKING_NAME) String parkingName,
                                                  @ApiParam(value = "Car type: Gasoline-powered, 20 kW and 50 " +
                                                          "kW power supply electric cars", required = true)
                                                  @RequestParam(Mappings.CAR_TYPE) String carType,
                                                  @ApiParam(value = "Slot identifier", required = true)
                                                  @RequestParam(Mappings.SLOT_NUMBER) String slotIdentifier,
                                                  @RequestParam(Mappings.NUMBER_HOURS) int numberHours) {

        if (!tollParkingService.isParkingNameValid(parkingName)) {

            ErrorResponse errorResponse = new ErrorResponse("Invalid parking name", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } else if (!tollParkingService.isCarTypeValid(carType)) {

            ErrorResponse errorResponse = new ErrorResponse("Invalid car type, we only serve " +
                    "Gasoline-powered cars, 20 kW and 50 kW power supply electric cars.", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } else if (!tollParkingService.isSlotNumberValid(carType, slotIdentifier)) {

            ErrorResponse errorResponse = new ErrorResponse("Invalid slot number", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } else {

            String responseEntityBody = tollParkingService.carLeavesGarage(parkingName, carType, slotIdentifier, numberHours);

            if (responseEntityBody != null && !responseEntityBody.isEmpty()) {
                return ResponseEntity.ok().body(responseEntityBody);
            } else {

                ErrorResponse errorResponse = new ErrorResponse("Something went wrong, most likely the slot" +
                        " number is not correct, could you please verify entered data?", System.currentTimeMillis());
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

            }

        }

    }

}