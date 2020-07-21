package com.bridgelabz.parkinglot.service;

public class Attendant {
    public int whereToPark(ParkingLotSystem parkingLotSystem) {
        return new ParkingLotOwner().askWhereToPark(parkingLotSystem);
    }
}
