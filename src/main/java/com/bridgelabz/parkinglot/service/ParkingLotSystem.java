package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    public static final int PARK_LOT_SIZE = 2;
    private final LinkedHashMap<Integer, SlotDetails> parkingLot = new LinkedHashMap<>();
    private final List<Object> vehicleRecord = new ArrayList<>();
    SlotDetails slotDetails = new SlotDetails();

    public ParkingLotSystem(int slots) {
        IntStream.rangeClosed(1, slots).forEach(slotNo -> parkingLot.put(slotNo, slotDetails));
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
        if (parkingLot.size() > PARK_LOT_SIZE && !parkingLot.containsValue(null))
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        parkingLot.put(slotNo, new SlotDetails(vehicle, LocalDateTime.now().withNano(0)));
        vehicleRecord.add(vehicle);
    }

    public boolean isPark(Object vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        if (!parkingLot.containsValue(getSlot(vehicle)))
            throw new ParkingLotException("No Such Vehicle Parked Yet", ParkingLotException.ExceptionType.NO_VEHICLE);
        return this.slotDetails != getSlot(vehicle);
    }

    public void isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public void unPark(Integer slotNo) {
        parkingLot.put(slotNo, slotDetails);
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        if (parkingLot.isEmpty())
            throw new ParkingLotException("Empty Parking Lot, Un-parked",
                    ParkingLotException.ExceptionType.EMPTY_PARKING_LOT);
        return isPark(vehicle);
    }

    public static Integer getKey(LinkedHashMap<Integer, SlotDetails> map, SlotDetails value) {
        return map.keySet().stream().filter(key -> value.equals(map.get(key))).findFirst().orElse(null);
    }

    public Integer getVacantSlot() {
        return getKey(parkingLot, slotDetails);
    }

    public SlotDetails getSlot(Object vehicle) {
        return parkingLot.values().stream().filter(slot -> vehicle.equals(slot.getVehicle()))
                .findFirst().orElse(null);
    }

    public Object findVehicle(Object vehicle) {
        return parkingLot.keySet().stream().filter(key -> getSlot(vehicle).equals(parkingLot.get(key)))
                .findFirst().orElse(null);
    }

    public LocalDateTime getParkingTime(Object slotNo) {
        return parkingLot.get(slotNo).getTime();
    }
}