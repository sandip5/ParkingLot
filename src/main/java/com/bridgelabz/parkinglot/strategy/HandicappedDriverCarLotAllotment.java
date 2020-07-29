package com.bridgelabz.parkinglot.strategy;

import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.List;

public class HandicappedDriverCarLotAllotment implements ILotAllotmentStrategy {
    @Override
    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        return parkingLots.stream().filter(parkingLot -> parkingLot.parkingSlotsMap.entrySet()
                .stream().anyMatch(entry -> entry.getValue() == null)).findFirst().orElse(null);
    }
}
