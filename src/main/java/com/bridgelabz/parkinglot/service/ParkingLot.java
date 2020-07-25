package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.model.SlotDetails;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingLot {
    int parkingLotSize;
    public Map<Integer, SlotDetails> parkingLotMap;


    public ParkingLot(int parkingLotSize) {
        this.parkingLotSize = parkingLotSize;
        parkingLotMap = new LinkedHashMap<>();
        initialiseParkingLot(parkingLotSize);
    }

    public void initialiseParkingLot(int lotSize) {
        for (int i = 1; i <= lotSize; i++) {
            parkingLotMap.put(i, null);
        }
    }

    public int getNumberOfVehicles() {
        int numberOfVehicles = 0;
        for (int i = 1; i <= parkingLotMap.size(); i++) {
            if (parkingLotMap.get(i) != null)
                numberOfVehicles++;
        }
        return numberOfVehicles;
    }
}
