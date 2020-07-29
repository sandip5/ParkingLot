package com.bridgelabz.parkinglot.strategy;

import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LargeCarLotAllotment implements ILotAllotmentStrategy {
    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        List<ParkingLot> parkingLotList = new ArrayList<>(parkingLots);
        parkingLotList.sort(Comparator.comparing(ParkingLot::getTotalNumberOfVehicleParked));
        return parkingLotList.get(0);
    }
}
