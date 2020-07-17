package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Driver;

public class ParkingLot {

    public ParkingLot parkingLotStore;
    public Integer slotId;
    public Driver driver;
    public ParkingLot parkingLot;

    public ParkingLot(Driver driver, int slotId) {
        this.driver = driver;
        this.slotId = slotId;
    }

    public ParkingLot(ParkingLot parkingLots) {
        this.parkingLotStore = parkingLots;
    }

    public ParkingLot() {

    }

    public Boolean isPark(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        return parkingLot.driver.car.carId != null;
    }

    public ParkingLot[] fillLot(ParkingLot... parking) throws ParkingLotException {
        try {
            int parkLotSize = 3;
            ParkingLot[] lotSpace = new ParkingLot[parkLotSize];
            lotSpace[0] = new ParkingLot(parking[0]);
            lotSpace[1] = new ParkingLot(parking[1]);
            lotSpace[2] = new ParkingLot(parking[2]);
            lotSpace[3] = new ParkingLot(parking[3]);
            return lotSpace;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParkingLotException("Lot Full So No More Can Park",
                    ParkingLotException.ExceptionType.LOT_SIZE_EXCEEDED);
        }
    }

    public boolean checkLot(ParkingLot[] lotSpace) {
        if(lotSpace.length == 4)
            return true;
        return false;
    }
}
