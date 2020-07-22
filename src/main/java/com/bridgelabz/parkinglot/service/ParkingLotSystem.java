package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    public static final int PARK_LOT_SIZE = 2;
    public final int PER_HOUR_CHARGE = 10;
    private final LinkedHashMap<Object, Object> parkingLot = new LinkedHashMap<>();
    private final List<Object> parkedVehicleHistory = new ArrayList<>();
    private int slotNo;

    public ParkingLotSystem(int slots) {
        IntStream.rangeClosed(1, slots).forEach(slotNo -> parkingLot.put(slotNo, " "));
    }

    public ParkingLotSystem() {
    }

    public boolean checkLot(int lotSpace) {
        return parkingLot.size() == lotSpace + 1;
    }

    public String freeSpace(int lotSpace) {
        return parkingLot.size() < lotSpace + 1 ? "Take Full Sign" : "Parking Lot Still Full";
    }

    public void park(Object slotNo, Object vehicle) throws ParkingLotException {
        if (slotNo == null || vehicle == null)
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        Object NOT_ALLOWED = 0;
        if (slotNo == NOT_ALLOWED || vehicle == NOT_ALLOWED)
            throw new ParkingLotException("Zero Entry Not Allowed", ParkingLotException.ExceptionType.ZERO_VALUE);
        if (parkingLot.containsValue(vehicle))
            throw new ParkingLotException("Duplicate Not Allowed", ParkingLotException.ExceptionType.DUPLICATE_ENTRY);
        if (parkingLot.size() > PARK_LOT_SIZE && !parkingLot.containsValue(" "))
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        parkingLot.put(slotNo, vehicle);
        this.slotNo = (int) slotNo;
        parkedVehicleHistory.add(vehicle);
    }

    public void park(Object vehicle) throws ParkingLotException {
        Object slotNo = getSlot();
        park(slotNo, vehicle);
    }

    public boolean isPark(Object vehicle) throws ParkingLotException {
        if (!parkedVehicleHistory.contains(vehicle))
            throw new ParkingLotException("No Such Vehicle Parked Yet", ParkingLotException.ExceptionType.NO_VEHICLE);
        return parkingLot.containsValue(vehicle);
    }

    public void isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public void unPark(Object slotNo) {
        parkingLot.put(slotNo, " ");
    }

    public int unPark(Integer slotNo, int durationOfParking) {
        unPark(slotNo);
        return PER_HOUR_CHARGE * durationOfParking;
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        return isPark(vehicle);
    }

    public <K, V> K getKey(Map<K, V> map, V value) {
        return map.keySet().stream().filter(key -> value.equals(map.get(key))).findFirst().orElse(null);
    }

    public Object findVehicle(Object vehicle) {
        return getKey(parkingLot, vehicle);
    }

    public Object getSlot() {
        return getKey(parkingLot, " ");
    }

    public Object getSlot(DriverCategory driverCategory) {
        if (DriverCategory.HANDICAPPED == driverCategory)
            return getSlot();
        Object slotNo = this.slotNo + 1;
        return slotNo;
    }
}
