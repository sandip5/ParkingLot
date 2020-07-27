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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    private final int numberOfLots;
    private final int numberOfSlots;
    private final List<ParkingSlot> parkingSlot;
    List<IParkingLotSystemObserver> systemObservers;

    public ParkingLot(int slots, int lots) {
        this.numberOfSlots = slots;
        this.numberOfLots = lots;
        parkingSlot = new ArrayList<>();
        systemObservers = new ArrayList<>();
        IntStream.range(0, lots).forEach(i -> parkingSlot.add(i, new ParkingSlot(lots)));

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
            throw new ParkingLotException("Duplicate Entry Not Allowed",
                    ParkingLotException.ExceptionType.DUPLICATE_ENTRY);
        if (checkAvailableSlot()) {
            systemObservers.forEach(IParkingLotSystemObserver::capacityIsFull);
            throw new ParkingLotException("Parking Space Full", ParkingLotException.ExceptionType.LOT_SIZE_FULL);
        }
        SlotDetails slotValue = new SlotDetails(parkedVehicleDetails, LocalDateTime.now().withNano(0), attendantName);
        ParkingSlot parkingSlot = getLot(parkedVehicleDetails);
        int slotNo = getSlot(parkingSlot);
        parkingSlot.parkingSlots.put(slotNo, slotValue);
    }

    public ParkingSlot getLot(ParkedVehicleDetails parkedVehicleDetails) {
        if (parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL) {
            List<ParkingSlot> parkingSlotList = new ArrayList<>(parkingSlot);
            parkingSlotList.sort(Comparator.comparing(ParkingSlot::getTotalNumberOfVehicleParked));
            return parkingSlotList.get(0);
        }
        if (parkedVehicleDetails.getDriverCategory() == DriverCategory.HANDICAPPED &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL) return getNearestFreeSpace(parkingSlot);
        return parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.LARGE ? getSlotForLargeVehicle(parkingSlot) : null;
    }

    public int getSlot(ParkingSlot parkingSlot) {
        return IntStream.rangeClosed(1, parkingSlot.parkingSlots.size()).filter(i -> parkingSlot.
                parkingSlots.get(i) == null).findFirst().orElse(0);
    }

    public boolean checkAvailableSlot() {
        int vehicleCount = parkingSlot.stream().mapToInt(ParkingSlot::getTotalNumberOfVehicleParked).sum();
        return (numberOfSlots * numberOfLots) == vehicleCount;
    }

    public boolean isPark(Object vehicle) {
        return parkingSlot.stream().flatMap(parkingSlot -> parkingSlot.parkingSlots.entrySet().stream())
                .filter(entry -> entry.getValue() != null).anyMatch(entry -> entry.getValue()
                        .getVehicleDetails().getVehicle().equals(vehicle));
    }

    public void isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public boolean unPark(Object vehicle) {
        for (ParkingSlot parkingSlot : this.parkingSlot) {
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                    Integer key = entry.getKey();
                    parkingSlot.parkingSlots.put(key, null);
                    systemObservers.forEach(IParkingLotSystemObserver::capacityIsAvailable);
                    return true;
                }
            }
        }
        return false;
    }

    public LocalDateTime getParkingTime(Object vehicle) {
        for (ParkingSlot parkingSlot : this.parkingSlot)
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                    Integer key = entry.getKey();
                    return parkingSlot.parkingSlots.get(key).getTime();
                }
            }
        return null;
    }

    public String getVehicleLocation(Object vehicle) {
        int lot = 0;
        for (ParkingSlot parkingSlot : this.parkingSlot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                    int slot = entry.getKey();
                    return "Lot :" + lot + "," + "Slot :" + slot;
                }
            }
        }
        return null;
    }

    public ParkingSlot getSlotForLargeVehicle(List<ParkingSlot> parkingSlots) {
        List<ParkingSlot> parkingSlotList = new ArrayList<>(parkingSlots);
        parkingSlotList.sort(Comparator.comparing(ParkingSlot::getTotalNumberOfVehicleParked));
        return parkingSlotList.get(0);
    }

    public ParkingSlot getNearestFreeSpace(List<ParkingSlot> parkingSlots) {
        return parkingSlots.stream().filter(parkingSlot -> parkingSlot.parkingSlots.entrySet()
                .stream().anyMatch(entry -> entry.getValue() == null)).findFirst().orElse(null);
    }

    public List<String> findLocationOfWhiteVehicle(VehicleColor color) {
        ArrayList<String> allWhiteVehicleLocation = new ArrayList<>();
        int lot = 0;
        for (ParkingSlot parkingSlot : this.parkingSlot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getColor().equals(color)) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allWhiteVehicleLocation.add(location);
                }
            }
        }
        return allWhiteVehicleLocation;
    }

    public List<String> findLocationOfBlueToyotaVehicle(VehicleColor color,
                                                        VehicleManufacturerName vehicleManufacturerName) {
        List<String> allBlueToyotaVehicleLocation = new ArrayList<>();
        int lot = 0;
        for (ParkingSlot parkingSlot : this.parkingSlot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getColor().equals(color) &&
                        entry.getValue()
                                .getVehicleDetails().getVehicleManufacturerName().equals(vehicleManufacturerName)) {
                    int slot = entry.getKey();
                    String vehicleDetails = "Lot :" + lot + "," + "Slot :" + slot +
                            "," + "Plate Number :" + entry.getValue().getVehicleDetails().getVehicle() + "," +
                            "Parking Attendant :" + entry.getValue().getAttendantName();
                    allBlueToyotaVehicleLocation.add(vehicleDetails);
                }
            }
        }
        return allBlueToyotaVehicleLocation;
    }

    public List<String> findLocationOfBmwVehicle(VehicleManufacturerName vehicleManufacturerName) {
        List<String> allBmwVehicleLocation = new ArrayList<>();
        int lot = 0;
        for (ParkingSlot parkingSlot : this.parkingSlot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicleManufacturerName()
                        .equals(vehicleManufacturerName)) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allBmwVehicleLocation.add(location);
                }
            }
        }
        return allBmwVehicleLocation;
    }

    public List<String> findLocationOfVehicleWhichParkedInLastThirtyMinutes(int minutes) {
        List<String> allVehicleLocationWhichParkedInLastThirtyMinute = new ArrayList<>();
        int lot = 0;
        for (ParkingSlot parkingSlot : this.parkingSlot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getTime().getMinute() < LocalDateTime.now()
                        .withMinute(minutes).getMinute()) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allVehicleLocationWhichParkedInLastThirtyMinute.add(location);
                }
            }
        }
        return allVehicleLocationWhichParkedInLastThirtyMinute;
    }

    public List<String> findLocationOfVehicleWhichParkedInSpecificLot(DriverCategory driverCategory, int findLot) {
        List<String> allHandicappedDriverVehicleLocation = new ArrayList<>();
        int lot = 0;
        for (ParkingSlot parkingSlot : this.parkingSlot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingSlot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && lot == findLot &&
                        entry.getValue().getVehicleDetails().getDriverCategory().equals(driverCategory)) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allHandicappedDriverVehicleLocation.add(location);
                }
            }
        }
        return allHandicappedDriverVehicleLocation;
    }

    public ArrayList<String> getAllVehiclePlateDetails() {
        return parkingSlot.stream()
                .flatMap(parkingSlot1 -> parkingSlot1.parkingSlots.entrySet().stream())
                .filter(entry -> entry.getValue() != null)
                .map(entry -> "Plate Number = " + entry.getValue().getVehicleDetails().getVehicle())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
