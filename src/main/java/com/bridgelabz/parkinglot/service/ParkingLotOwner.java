package com.bridgelabz.parkinglot.service;

public class ParkingLotOwner {
    public String getStatus(int parkLotSize, ParkingLotSystem parkingLotSystem) {
        boolean parkingLotStatus = parkingLotSystem.checkLot(parkLotSize);
        return !parkingLotStatus ? "Parking Lot Not Full" : "Parking Lot Full";
    }
}