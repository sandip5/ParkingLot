package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.DriverCategory;

public class ParkingLotOwner {

    public ParkingLotOwner() {
    }

    public String getStatus(int parkLotSize, ParkingLotSystem parkingLotSystem) {
        boolean parkingLotStatus = parkingLotSystem.checkLot(parkLotSize);
        return !parkingLotStatus ? "Parking Lot Not Full" : "Parking Lot Full";
    }

    public Object askWhereToPark(ParkingLotSystem parkingLotSystem, DriverCategory driverCategory) {
        return parkingLotSystem.getSlot(driverCategory);
    }
}