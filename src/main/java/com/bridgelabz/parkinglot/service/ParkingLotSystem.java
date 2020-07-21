package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotSystem {
    public static final int PARK_LOT_SIZE = 2;
    public int PER_HOUR_CHARGE = 10;
    public final LinkedHashMap<Integer, Object> parkingLot = new LinkedHashMap<>();
    public final List<Object> parkedVehicleHistory = new ArrayList<>();
    private int slotNo;
    private VehicleDetails vehicleDetails;

    public ParkingLotSystem(int slots) {
        for (int slotNo = 1; slotNo <= slots; slotNo++) {
            parkingLot.put(slotNo, " ");
        }
    }

    public ParkingLotSystem() { }

    public boolean checkLot(int lotSpace) {
        return parkingLot.size() == lotSpace + 1;
    }

    public String freeSpace(int lotSpace) {
        return parkingLot.size() < lotSpace + 1 ? "Take Full Sign" : "Parking Lot Still Full";
    }

    public void park(Integer slotNo, Object vehicle) throws ParkingLotException {
        if (slotNo == null || vehicle == null)
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        Object NOT_ALLOWED = 0;
        if (slotNo == NOT_ALLOWED || vehicle == NOT_ALLOWED)
            throw new ParkingLotException("Zero Entry Not Allowed", ParkingLotException.ExceptionType.ZERO_VALUE);
        if (parkingLot.size() > PARK_LOT_SIZE && !parkingLot.containsValue(" "))
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        parkingLot.put(slotNo, vehicle);
        parkedVehicleHistory.add(vehicle);
    }

    public boolean isPark(Object vehicle) throws ParkingLotException {
        if (!parkedVehicleHistory.contains(vehicle))
            throw new ParkingLotException("No Such Vehicle Parked Yet", ParkingLotException.ExceptionType.NO_VEHICLE);
        return parkingLot.containsValue(vehicle);
    }

    public void isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public void unPark(Integer slotNo) {
        parkingLot.put(slotNo, " ");
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        if (parkingLot.isEmpty())
            throw new ParkingLotException("Empty Parking Lot, Un-parked",
                    ParkingLotException.ExceptionType.EMPTY_PARKING_LOT);
        return isPark(vehicle);
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        return map.keySet().stream().filter(key -> value.equals(map.get(key))).findFirst().orElse(null);
    }

    public int getVacantSlot() {
        return getKey(parkingLot, " ");
    }

    public int findVehicle(Object vehicle) {
        return parkingLot.keySet().stream().filter(key -> vehicle.equals(parkingLot.get(key))).findFirst().orElse(null);
    }

    public int unPark(Integer slotNo, VehicleDetails vehicle) {
        parkingLot.put(slotNo, " ");
//        int charges = PER_HOUR_CHARGE * vehicle.
        return 0;
    }
}
