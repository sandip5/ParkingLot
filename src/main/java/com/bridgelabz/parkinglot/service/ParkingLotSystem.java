package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleManufacturerName;
import com.bridgelabz.parkinglot.enums.VehicleSize;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;
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
    private Integer key;

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


    public void park(ParkedVehicleDetails parkedVehicleDetails, String attendantName) throws ParkingLotException {
        if (parkedVehicleDetails == null)
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        Object NOT_ALLOWED = 0;
        if (parkedVehicleDetails.getVehicle() == NOT_ALLOWED)
            throw new ParkingLotException("Zero Entry Not Allowed", ParkingLotException.ExceptionType.ZERO_VALUE);
        if (isPark(parkedVehicleDetails.getVehicle()))
            throw new ParkingLotException("Duplicate Entry Not Allowed", ParkingLotException.ExceptionType.DUPLICATE_ENTRY);
        if (checkAvailableSlot()) {
            systemObservers.forEach(IParkingLotSystemObserver::capacityIsFull);
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        }
        SlotDetails slotValue = new SlotDetails(parkedVehicleDetails, LocalDateTime.now().withNano(0), attendantName);
        ParkingLot parkingLot = getLot(this.parkingLot, parkedVehicleDetails);
        int slotNo = getSpot(parkingLot);
        parkingLot.parkingLotMap.put(slotNo, slotValue);
        System.out.println(parkingLot.parkingLotMap);
    }

    public ParkingLot getLot(List<ParkingLot> parkingLots, ParkedVehicleDetails parkedVehicleDetails) {
        if (parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL) {
            List<ParkingLot> parkingLotList = new ArrayList<>(parkingLots);
            parkingLotList.sort(Comparator.comparing(parkingLot -> parkingLot.getNumberOfVehicles()));
            return parkingLotList.get(0);
        } else if (parkedVehicleDetails.getDriverCategory() == DriverCategory.HANDICAPPED &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL) {
            return getNearestFreeSpace(parkingLots);
        } else if (parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.LARGE) {
            return getSlotForLargeVehicle(parkingLots);
        }
        return null;
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
                    if (entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
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
                    if (entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        System.out.println(key);
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
                    if (entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
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
                    if (entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                        Integer key = entry.getKey();
                        return "Lot :" + counter + "," + "Slot :" + key;
                    }
                }
            }
        }
        return null;
    }

    public ParkingLot getSlotForLargeVehicle(List<ParkingLot> parkingLots) {
        List<ParkingLot> parkingLotList = new ArrayList<>(parkingLots);
        parkingLotList.sort(Comparator.comparing(parkingLot -> parkingLot.getNumberOfVehicles()));
        return parkingLotList.get(0);
    }

    public ParkingLot getNearestFreeSpace(List<ParkingLot> parkingLots) {
        for (ParkingLot parkingLot : parkingLots) {
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() == null) {
                    return parkingLot;
                }
            }
        }
        return null;
    }

    public List findLocationOfWhiteVehicle(VehicleColor color) {
        List allWhiteVehicleLocation = new ArrayList();
        int counter = 0;
        for (ParkingLot parkingLot : parkingLot) {
            counter++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicleDetails().getColor().equals(color)) {
                        Integer key = entry.getKey();
                        String location = "Lot :" + counter + "," + "Slot :" + key;
                        allWhiteVehicleLocation.add(location);
                    }
                }
            }
        }
        return allWhiteVehicleLocation;
    }

    public List findLocationOfBlueToyotaVehicle(VehicleColor color, VehicleManufacturerName vehicleManufacturerName) {
        List allBlueToyotaVehicleLocation = new ArrayList();
        int counter = 0;
        for (ParkingLot parkingLot : parkingLot) {
            counter++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicleDetails().getColor().equals(color) && entry.getValue()
                            .getVehicleDetails().getVehicleManufacturerName().equals(vehicleManufacturerName)) {
                        Integer key = entry.getKey();
                        String vehicleDetails = "Lot :" + counter + "," + "Slot :" + key +
                                "," + "Plate Number :" + entry.getValue().getVehicleDetails().getVehicle() + "," +
                                "Parking Attendant :" + entry.getValue().getAttendantName();
                        allBlueToyotaVehicleLocation.add(vehicleDetails);
                    }
                }
            }
        }
        return allBlueToyotaVehicleLocation;
    }

    public List findLocationOfBmwVehicle(VehicleManufacturerName vehicleManufacturerName) {
        List allBmwVehicleLocation = new ArrayList();
        int counter = 0;
        for (ParkingLot parkingLot : parkingLot) {
            counter++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingLotMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue().getVehicleDetails().getVehicleManufacturerName()
                            .equals(vehicleManufacturerName)) {
                        Integer key = entry.getKey();
                        String location = "Lot :" + counter + "," + "Slot :" + key;
                        allBmwVehicleLocation.add(location);
                    }
                }
            }
        }
        return allBmwVehicleLocation;
    }
}