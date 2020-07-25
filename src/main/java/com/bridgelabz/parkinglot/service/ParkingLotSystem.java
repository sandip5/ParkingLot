package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.SlotDetails;
import com.bridgelabz.parkinglot.observer.IParkingLotSystemObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ParkingLotSystem {
    private final int numberOfLots;
    private final int lotSize;
    public List<ParkingLot> parkingLot;
    List<IParkingLotSystemObserver> systemObservers;

    public ParkingLotSystem(int slots) {
        this.lotSize = slots;
        this.numberOfLots = slots;
        parkingLot = new ArrayList<>();
        systemObservers = new ArrayList<>();
        for (int i = 0; i < slots; i++) {
            parkingLot.add(i, new ParkingLot(slots));
        }

    }

    public void registerListener(IParkingLotSystemObserver observer) {
        this.systemObservers.add(observer);
    }


    public void park(DriverCategory driverCategory, Object vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        Object NOT_ALLOWED = 0;
        if (vehicle == NOT_ALLOWED)
            throw new ParkingLotException("Zero Entry Not Allowed", ParkingLotException.ExceptionType.ZERO_VALUE);
        if (isPark(vehicle))
            throw new ParkingLotException("Duplicate Entry Not Allowed", ParkingLotException.ExceptionType.DUPLICATE_ENTRY);
        if (checkAvailableSlot()) {
            systemObservers.forEach(IParkingLotSystemObserver::capacityIsFull);
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        }
        SlotDetails slotValue = new SlotDetails(vehicle, LocalDateTime.now().withNano(0));
        ParkingLot parkingLot;
        int slotNo;
        if(DriverCategory.NORMAL == driverCategory){
            parkingLot = getLot(this.parkingLot);
        } else {
            parkingLot = getNearestFreeSpace();
        }
        slotNo = getSpot(parkingLot);
        parkingLot.parkingLotMap.put(slotNo, slotValue);
    }

    public ParkingLot getLot(List<ParkingLot> parkingLots) {
        parkingLots.sort(Comparator.comparing(ParkingLot::getNumberOfVehicles));
        return parkingLots.get(0);
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
        return (lotSize * numberOfLots) == vehicleCount;
    }

    public boolean isPark(Object vehicle) {
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
        for (ParkingLot parkingLot : parkingLot)
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        parkingLot.parkingLotMap.put(key, null);
                        systemObservers.forEach(IParkingLotSystemObserver::capacityIsAvailable);
                        return true;
                    }
                }
            }
        return false;
    }

    public LocalDateTime getParkingTime(Object vehicle) {
        for (ParkingLot parkingLot : parkingLot)
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        return parkingLot.parkingLotMap.get(key).getTime();
                    }
                }
            }
        return null;
    }

    public String getVehicleLocation(Object vehicle) {
        int counter = 0;
        for (ParkingLot parkingLot : parkingLot) {
            counter++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        return "Lot :" + counter + "," + "Slot :" + key;
                    }
                }
            }
        }
        return null;
    }

    public ParkingLot getNearestFreeSpace(){
        for (ParkingLot parkingLot : parkingLot)
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() == null) {
                    return parkingLot;
                }
            }
        return null;
    }
}