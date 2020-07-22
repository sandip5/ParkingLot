package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.DriverCategory;

public class Attendant {
    public Object whereToPark(ParkingLotSystem parkingLotSystem, DriverCategory driverCategory) {
        return new ParkingLotOwner().askWhereToPark(parkingLotSystem, driverCategory);
    }
}
