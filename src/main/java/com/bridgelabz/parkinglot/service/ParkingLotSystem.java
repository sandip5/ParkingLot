package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ParkingLotSystem {
    public static final int PARK_LOT_SIZE = 2;
    public final LinkedHashMap<Integer, Object> parkingLot = new LinkedHashMap<>();
    public final List parkedVehicleHistory = new ArrayList();

    public ParkingLotSystem() {
    }

    public boolean checkLot(int lotSpace) {
        if (parkingLot.size() == lotSpace + 1)
            return true;
        System.out.println(parkingLot);
        return false;
    }

    public String freeSpace(int lotSpace) {
        if (parkingLot.size() < lotSpace + 1)
            return "Take Full Sign";
        return "Parking Lot Still Full";
    }

    public void park(Integer ticketNo, Object vehicle) throws ParkingLotException {
        try {
            if (ticketNo == null || vehicle == null) {
                throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
            } else {
                Object NOT_ALLOWED = 0;
                if (vehicle == NOT_ALLOWED) {
                    throw new ParkingLotException("Zero Entry Not Allowed", ParkingLotException.ExceptionType.ZERO_VALUE);
                } else {
                    if (parkingLot.size() > PARK_LOT_SIZE) {
                        throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_EXCEEDED);
                    } else {
                        if (parkingLot.containsKey(ticketNo) && parkingLot.containsValue(vehicle))
                            throw new ParkingLotException("Duplicate Not Allowed", ParkingLotException.ExceptionType.DUPLICATE_ENTRY);
                    }
                    parkingLot.put(ticketNo, vehicle);
                    parkedVehicleHistory.add(vehicle);
                }
            }
        } catch (NullPointerException e) {
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        }
    }

    public boolean isPark(Object vehicle) throws ParkingLotException {
        if (!parkedVehicleHistory.contains(vehicle))
            throw new ParkingLotException("No Such Vehicle Parked Yet", ParkingLotException.ExceptionType.NO_VEHICLE);
        else {
            return parkingLot.containsValue(vehicle);
        }
    }

    public boolean isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public void unPark(Integer ticketNo) {
        parkingLot.remove(ticketNo);
        System.out.println(parkingLot);
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        if (parkingLot.isEmpty())
            throw new ParkingLotException("Empty Parking Lot, Un-parked", ParkingLotException.ExceptionType.EMPTY_PARKING_LOT);
        return isPark(vehicle);
    }
}
