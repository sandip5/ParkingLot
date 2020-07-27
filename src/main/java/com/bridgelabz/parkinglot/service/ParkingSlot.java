package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.model.SlotDetails;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingSlot {
    int parkingLotSize;
    public Map<Integer, SlotDetails> parkingSlots;

    public ParkingSlot(int parkingLotSize) {
        this.parkingLotSize = parkingLotSize;
        parkingSlots = new LinkedHashMap<>();
        initialiseParkingLot(parkingLotSize);
    }

    public void initialiseParkingLot(int slotSize) {
        IntStream.rangeClosed(1, slotSize).forEach(i -> parkingSlots.put(i, null));
    }

    public int getTotalNumberOfVehicleParked() {
        int totalNumberOfParkedVehicles = (int) IntStream.rangeClosed(1, parkingSlots.size())
                .filter(i -> parkingSlots.get(i) != null).count();
        return totalNumberOfParkedVehicles;
    }
}
