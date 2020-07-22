package com.bridgelabz.parkinglot.service;

public class Attendant {
    public Object whereToPark(ParkingLotSystem parkingLotSystem, DriverCategory driverCategory) {
        return new ParkingLotOwner().askWhereToPark(parkingLotSystem, driverCategory);
    }
}
