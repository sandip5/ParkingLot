package com.bridgelabz.parkinglot.strategy;

import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.List;

public interface ILotAllotmentStrategy {
    ParkingLot getLot(List<ParkingLot> parkingLot);
}
