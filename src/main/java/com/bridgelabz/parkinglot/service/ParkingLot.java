package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Driver;

import java.util.stream.IntStream;

public class ParkingLot {

    public ParkingLot parkingLotStore;
    public Integer slotId;
    public Driver driver;
    public ParkingLot parkingLot;
    public int parkLotSize = 3;
    ParkingLot[] lotSize = new ParkingLot[parkLotSize];

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
            IntStream.rangeClosed(0, parking.length - 1).forEach(index -> lotSize[index] = parking[index]);
            return lotSize;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParkingLotException("Lot Full So No More Car Park",
                    ParkingLotException.ExceptionType.LOT_SIZE_EXCEEDED);
        }
    }

    public boolean checkLot(ParkingLot[] lotSpace) throws ParkingLotException {
        return IntStream.rangeClosed(0, lotSpace.length - 1).noneMatch(index -> lotSpace[index] == null);
    }
}
