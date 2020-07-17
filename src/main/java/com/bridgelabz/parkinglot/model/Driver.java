package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.service.ParkingLot;

public class Driver {

    public Car car;

    public Driver(Car car) {
        this.car = car;
    }

    public ParkingLot unPark(ParkingLot parkingLot) {
        return null;
    }

    public Boolean goHome(ParkingLot unParkParkingLot) {
        if (unParkParkingLot == null)
            return true;
        return false;
    }
}
