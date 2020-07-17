package com.bridgelabz.parkinglot.test;

public class ParkingLot {

    public Integer slotId;
    public Driver driver;
    public ParkingLot parkingLot;

    public ParkingLot(Driver driver, int slotId) {
        this.driver = driver;
        this.slotId = slotId;
    }

    public Boolean isPark(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        if(parkingLot.slotId == null)
            return false;
        return true;
    }
}
