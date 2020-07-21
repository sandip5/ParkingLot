package com.bridgelabz.parkinglot.service;

public class Attendant {
    public Object whereToPark(ParkingLotSystem parkingLotSystem) {
        return new ParkingLotOwner().askWhereToPark(parkingLotSystem);
    }
}
