package com.bridgelabz.parkinglot.service;

public class ParkingLotOwner {

    private Boolean parkingStatus;

    public Boolean getFlight(Boolean parkingStatus) {
        this.parkingStatus = parkingStatus;
        return parkingStatus;
    }
}
