package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.service.ParkingLot;

public class Driver {

    public Car car;

    public Driver(Car car) {
        this.car = car;
    }

    public ParkingLot unPark() {
        return null;
    }

    public Boolean goHome(ParkingLot unParkParkingLot) {
        return unParkParkingLot == null;
    }
}
