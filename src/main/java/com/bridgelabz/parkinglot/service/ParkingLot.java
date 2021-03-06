package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.model.SlotDetails;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLot {
    int parkingLotSize;
    public Map<Integer, SlotDetails> parkingSlotsMap;

    public ParkingLot(int parkingLotSize) {
        this.parkingLotSize = parkingLotSize;
        parkingSlotsMap = new LinkedHashMap<>();
        initialiseParkingLot(parkingLotSize);
    }

    public void initialiseParkingLot(int slotSize) {
        IntStream.rangeClosed(1, slotSize).forEach(i -> parkingSlotsMap.put(i, null));
    }

    public int getTotalNumberOfVehicleParked() {
        return (int) IntStream.rangeClosed(1, parkingSlotsMap.size())
                .filter(i -> parkingSlotsMap.get(i) != null).count();
    }
}
