package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParkingLotSystem {
    public static final int PARK_LOT_SIZE = 2;
    public static final LinkedHashMap<Integer, Object> parkingLot = new LinkedHashMap<>();
//    public static final LinkedHashMap<Object, LocalDateTime> vehicleParkedTime = new LinkedHashMap<>();
    LocalDateTime time = null;

    public ParkingLotSystem(int slots) {
        for (int slotNo = 1; slotNo <= slots; slotNo++) {
            parkingLot.put(slotNo, " ");
        }
    }

    public ParkingLotSystem() {
    }

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
        time = LocalDateTime.now().withNano(0);
        SlotDetails slotDetails = new SlotDetails(vehicle, time);
        parkingLot.put(slotNo, slotDetails);
        System.out.println(parkingLot);
//        vehicleParkedTime.put(vehicle, time);
    }

    public boolean isPark(Object vehicle) throws ParkingLotException {
//        if (!vehicleParkedTime.containsKey(vehicle))
//            throw new ParkingLotException("No Such Vehicle Parked Yet", ParkingLotException.ExceptionType.NO_VEHICLE);
//        return parkingLot.containsValue(vehicle);
        return true;
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
}
