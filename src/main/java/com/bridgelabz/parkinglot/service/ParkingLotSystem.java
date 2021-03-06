package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleManufacturerName;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;
import com.bridgelabz.parkinglot.model.SlotDetails;
import com.bridgelabz.parkinglot.observer.IParkingLotSystemObserver;
import com.bridgelabz.parkinglot.strategy.LotAllotmentFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private final int numberOfLots;
    private final int numberOfSlots;
    private final List<ParkingLot> parkingLotList;
    List<IParkingLotSystemObserver> systemObservers;

    public ParkingLotSystem(int slots, int lots) {
        this.numberOfSlots = slots;
        this.numberOfLots = lots;
        parkingLotList = new ArrayList<>();
        systemObservers = new ArrayList<>();
        IntStream.range(0, lots).forEach(i -> parkingLotList.add(i, new ParkingLot(lots)));

    }

    public void registerListener(IParkingLotSystemObserver observer) {
        this.systemObservers.add(observer);
    }


    public void park(ParkedVehicleDetails parkedVehicleDetails, String attendantName) throws ParkingLotException {
        if (parkedVehicleDetails == null)
            throw new ParkingLotException("Null Entry Not Allowed", ParkingLotException.ExceptionType.NULL_VALUE);
        Integer NOT_ALLOWED = 0;
        if (parkedVehicleDetails.getPlateNumber() == NOT_ALLOWED)
            throw new ParkingLotException("Zero Entry Not Allowed", ParkingLotException.ExceptionType.ZERO_VALUE);
        if (isPark(parkedVehicleDetails.getPlateNumber()))
            throw new ParkingLotException("Duplicate Entry Not Allowed",
                    ParkingLotException.ExceptionType.DUPLICATE_ENTRY);
        if (checkAvailableSlot()) {
            systemObservers.forEach(IParkingLotSystemObserver::capacityIsFull);
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        }
        SlotDetails slotValue = new SlotDetails(parkedVehicleDetails, LocalDateTime.now().withNano(0), attendantName);
        ParkingLot parkingLot = getLot(parkedVehicleDetails);
        int slotNo = getSlot(parkingLot);
        parkingLot.parkingSlotsMap.put(slotNo, slotValue);
    }

    public ParkingLot getLot(ParkedVehicleDetails parkedVehicleDetails) {
        LotAllotmentFactory lotAllotmentFactory = new LotAllotmentFactory();
        return lotAllotmentFactory.getLotAllotmentStrategy(parkedVehicleDetails).getLot(parkingLotList);
    }

    public int getSlot(ParkingLot parkingLot) {
        return IntStream.rangeClosed(1, parkingLot.parkingSlotsMap.size()).filter(i -> parkingLot.
                parkingSlotsMap.get(i) == null).findFirst().orElse(0);
    }

    public boolean checkAvailableSlot() {
        int vehicleCount = parkingLotList.stream().mapToInt(ParkingLot::getTotalNumberOfVehicleParked).sum();
        return (numberOfSlots * numberOfLots) == vehicleCount;
    }

    public boolean isPark(Object vehicle) {
        return parkingLotList.stream().flatMap(parkingLot -> parkingLot.parkingSlotsMap.entrySet().stream())
                .filter(entry -> entry.getValue() != null).anyMatch(entry -> entry.getValue()
                        .getVehicleDetails().getPlateNumber().equals(vehicle));
    }

    public void isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public boolean unPark(Integer vehicle) {
        for (ParkingLot parkingLot : parkingLotList) {
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getPlateNumber().equals(vehicle)) {
                    Integer key = entry.getKey();
                    parkingLot.parkingSlotsMap.put(key, null);
                    systemObservers.forEach(IParkingLotSystemObserver::capacityIsAvailable);
                    return true;
                }
        }
        return false;
    }

    public LocalDateTime getParkingTime(Integer vehicle) {
        for (ParkingLot parkingLot : parkingLotList)
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getPlateNumber().equals(vehicle)) {
                    Integer key = entry.getKey();
                    return parkingLot.parkingSlotsMap.get(key).getTime();
                }
        return null;
    }

    public String getVehicleLocation(Object vehicle) {
        int lot = 0;
        for (ParkingLot parkingLot : parkingLotList) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getPlateNumber().equals(vehicle)) {
                    int slot = entry.getKey();
                    return "Lot :" + lot + "," + "Slot :" + slot;
                }
        }
        return null;
    }

    public List<String> findLocationOfVehicleAsPerColor(VehicleColor color) {
        ArrayList<String> allVehicleLocationAsPerSpecificColor = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLotList) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getColor().equals(color)) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allVehicleLocationAsPerSpecificColor.add(location);
                }
        }
        return allVehicleLocationAsPerSpecificColor;
    }

    public List<String> findLocationOfVehicleAsPerColorAndCompanyName(VehicleColor color,
                                                                      VehicleManufacturerName vehicleManufacturerName) {
        List<String> allVehicleLocationAsPerColorAndCompanyName = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLotList) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getColor().equals(color) &&
                        entry.getValue()
                                .getVehicleDetails().getVehicleManufacturerName().equals(vehicleManufacturerName)) {
                    int slot = entry.getKey();
                    String vehicleDetails = "Lot :" + lot + "," + "Slot :" + slot +
                            "," + "Plate Number :" + entry.getValue().getVehicleDetails().getPlateNumber() + "," +
                            "Parking Attendant :" + entry.getValue().getAttendantName();
                    allVehicleLocationAsPerColorAndCompanyName.add(vehicleDetails);
                }
        }
        return allVehicleLocationAsPerColorAndCompanyName;
    }

    public List<String> findLocationOfVehicleAsPerCompanyName(VehicleManufacturerName vehicleManufacturerName) {
        List<String> allVehicleLocationAsPerCompanyName = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLotList) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicleManufacturerName()
                        .equals(vehicleManufacturerName)) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allVehicleLocationAsPerCompanyName.add(location);
                }
        }
        return allVehicleLocationAsPerCompanyName;
    }

    public List<String> findLocationOfVehicleAsPerTime(int minutes) {
        List<String> allVehicleLocationAsPerTime = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLotList) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null
                        && Duration.between(entry.getValue().getTime(), LocalDateTime.now()).toMinutes() <= minutes) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allVehicleLocationAsPerTime.add(location);
                }
        }
        return allVehicleLocationAsPerTime;
    }

    public List<String> findLocationOfVehicleAsPerLotAndDriverCategory(DriverCategory driverCategory, int findLot) {
        List<String> allVehicleLocationAsPerGivenDriverCategoryAndLot = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLotList) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlotsMap.entrySet())
                if (entry.getValue() != null && lot == findLot &&
                        entry.getValue().getVehicleDetails().getDriverCategory().equals(driverCategory)) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allVehicleLocationAsPerGivenDriverCategoryAndLot.add(location);
                }
        }
        return allVehicleLocationAsPerGivenDriverCategoryAndLot;
    }

    public ArrayList<String> getAllVehiclePlateDetails() {
        return parkingLotList.stream()
                .flatMap(parkingLot1 -> parkingLot1.parkingSlotsMap.entrySet().stream())
                .filter(entry -> entry.getValue() != null)
                .map(entry -> "Plate Number = " + entry.getValue().getVehicleDetails().getPlateNumber())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
