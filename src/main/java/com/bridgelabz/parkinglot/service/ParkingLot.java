package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Driver;

import java.util.stream.IntStream;

public class ParkingLot {

    public ParkingLot parkingLotStore;
    public Integer slotId;
    public Driver driver;
    public ParkingLot parkingLot;

    public ParkingLot(Driver driver, int slotId) {
        this.driver = driver;
        this.slotId = slotId;
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
            IntStream.rangeClosed(0, parking.length - 1).forEach(index -> lotSpace[index] = parking[index]);
            return lotSpace;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParkingLotException("Lot Full So No More Car Park",
                    ParkingLotException.ExceptionType.LOT_SIZE_EXCEEDED);
        }
    }

    public boolean checkLot(ParkingLot[] lotSpace) throws ParkingLotException {
        return lotSpace.length == fillLot().length;
    }
}
