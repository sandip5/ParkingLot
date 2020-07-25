package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    public static final int PARK_LOT_SIZE = 2;
    private final LinkedHashMap<Integer, SlotDetails> parkingSlots = new LinkedHashMap<>();
    private int numberOfLots;
    private int lotSize;
    public List<ParkingLot> parkingLot;
    private List<Object> vehicleRecord = new ArrayList<>();
    SlotDetails slotDetails = new SlotDetails();

    public ParkingLotSystem(int slots) {
        this.lotSize = slots;
        this.numberOfLots = slots;
        parkingLot = new ArrayList();
        for (int i = 0; i < slots; i++) {
            parkingLot.add(i, new ParkingLot(slots));
        }

    }
    public boolean checkLot(int lotSpace) {
        return parkingSlots.size() == lotSpace + 1;
    }

    public String freeSpace(int lotSpace) {
        return parkingSlots.size() < lotSpace + 1 ? "Take Full Sign" : "Parking Lot Still Full";
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        Object NOT_ALLOWED = 0;
        if (vehicle == NOT_ALLOWED)
            throw new ParkingLotException("Zero Entry Not Allowed", ParkingLotException.ExceptionType.ZERO_VALUE);
        if (checkAvailableSlot())
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        if (isPark(vehicle))
            throw new ParkingLotException("Duplicate Entry Not Allowed", ParkingLotException.ExceptionType.DUPLICATE_ENTRY);
        SlotDetails slotValue = new SlotDetails(vehicle, LocalDateTime.now().withNano(0));
        ParkingLot parkingLot = getLot(this.parkingLot);
        Integer slot1 = getSpot(parkingLot);
        parkingLot.parkingLotMap.put(slot1, slotValue);
        vehicleRecord.add(vehicle);
    }

    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        List<ParkingLot> parkingLotList = parkingLots;
        Collections.sort(parkingLotList, Comparator.comparing(parkingLot -> parkingLot.getNumberOfVehicles()));
        return parkingLotList.get(0);
    }

    public Integer getSpot(ParkingLot parkingLot) {
        for (int i = 1; i <= parkingLot.parkingLotMap.size(); i++) {
            if (parkingLot.parkingLotMap.get(i) == null)
                return i;
        }
        return null;
    }

    public boolean checkAvailableSlot() {
        int vehicleCount = parkingLot.stream().mapToInt(ParkingLot::getNumberOfVehicles).sum();
        boolean availability = (lotSize * numberOfLots) == vehicleCount;
        return availability;
    }

    public boolean isPark(Object vehicle){
        for (ParkingLot parkingLot : parkingLot)
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        return true;
                    }
                }
            }
        return false;
    }

    public void isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public boolean unPark(Object vehicle) {
        ParkingLot parkingLot = getLotOfVehicle(vehicle);
        if (parkingLot == null)
            return false;
        for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
            if (vehicle.equals(entry.getValue())) {
                Integer key = entry.getKey();
                parkingLot.parkingLotMap.put(key, null);
                return true;
            }
        }
        return false;
    }

    public ParkingLot getLotOfVehicle(Object vehicle) {
        for (ParkingLot parkingLotObject : parkingLot)
            for (Map.Entry<Integer, SlotDetails> entry : parkingLotObject.parkingLotMap.entrySet()) {
                if(entry.getValue() == null)
                    return parkingLotObject;
                if (entry.getValue().getVehicle() == vehicle) {
                    return parkingLotObject;
                }
            }
        return null;
    }

//    public void unPark(Integer slotNo) {
//        parkingSlots.put(slotNo, slotDetails);
//    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        if (parkingSlots.isEmpty())
            throw new ParkingLotException("Empty Parking Lot, Un-parked",
                    ParkingLotException.ExceptionType.EMPTY_PARKING_LOT);
        return isPark(vehicle);
    }

    public static Integer getKey(LinkedHashMap<Integer, SlotDetails> map, SlotDetails value) {
        for (Integer key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    public Integer getVacantSlot() {
        return getKey(parkingSlots, slotDetails);
    }

    public SlotDetails getSlot(Object vehicle) {
        for (SlotDetails slot : parkingSlots.values()) {
            if (vehicle.equals(slot.getVehicle())) {
                return slot;
            }
        }
        return null;
    }

    public Object findVehicle(Object vehicle) {
        return parkingSlots.keySet().stream().filter(key -> getSlot(vehicle).equals(parkingSlots.get(key)))
                .findFirst().orElse(null);
    }

    public LocalDateTime getParkingTime(Object slotNo) {
        return parkingSlots.get(slotNo).getTime();
    }

}