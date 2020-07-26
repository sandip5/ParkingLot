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

public class ParkingLotSystem {
    private final int numberOfLots;
    private final int numberOfSlots;
    private final List<ParkingLot> parkingLot;
    List<IParkingLotSystemObserver> systemObservers;

    public ParkingLotSystem(int slots, int lots) {
        this.numberOfSlots = slots;
        this.numberOfLots = lots;
        parkingLot = new ArrayList<>();
        systemObservers = new ArrayList<>();
        IntStream.range(0, lots).forEach(i -> parkingLot.add(i, new ParkingLot(lots)));

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
        int slotNo = getSlot(parkingLot);
        parkingLot.parkingSlots.put(slotNo, slotValue);
    }

    public ParkingLot getLot(List<ParkingLot> parkingLots, ParkedVehicleDetails parkedVehicleDetails) {
        if (parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL) {
            List<ParkingLot> parkingLotList = new ArrayList<>(parkingLots);
            parkingLotList.sort(Comparator.comparing(ParkingLot::getTotalNumberOfVehicleParked));
            return parkingLotList.get(0);
        }
        if (parkedVehicleDetails.getDriverCategory() == DriverCategory.HANDICAPPED &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL) return getNearestFreeSpace(parkingLots);
        return parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.LARGE ? getSlotForLargeVehicle(parkingLots) : null;
    }

    public int getSlot(ParkingLot parkingLot) {
        return IntStream.rangeClosed(1, parkingLot.parkingSlots.size()).filter(i -> parkingLot.
                parkingSlots.get(i) == null).findFirst().orElse(0);
    }

    public boolean checkAvailableSlot() {
        int vehicleCount = parkingLot.stream().mapToInt(ParkingLot::getTotalNumberOfVehicleParked).sum();
        return (numberOfSlots * numberOfLots) == vehicleCount;
    }

    public boolean isPark(Object vehicle) {
        return parkingLot.stream().flatMap(parkingLot -> parkingLot.parkingSlots.entrySet().stream())
                .filter(entry -> entry.getValue() != null).anyMatch(entry -> entry.getValue()
                        .getVehicleDetails().getVehicle().equals(vehicle));
    }

    public void isPark() throws ParkingLotException {
        throw new ParkingLotException("Not Given Any Input", ParkingLotException.ExceptionType.ENTER_INPUT);
    }

    public boolean unPark(Object vehicle) {
        for (ParkingLot parkingLot : parkingLot) {
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                    Integer key = entry.getKey();
                    parkingLot.parkingSlots.put(key, null);
                    systemObservers.forEach(IParkingLotSystemObserver::capacityIsAvailable);
                    return true;
                }
            }
        }
        return false;
    }

    public LocalDateTime getParkingTime(Object vehicle) {
        for (ParkingLot parkingLot : parkingLot)
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                    Integer key = entry.getKey();
                    return parkingLot.parkingSlots.get(key).getTime();
                }
            }
        return null;
    }

    public String getVehicleLocation(Object vehicle) {
        int lot = 0;
        for (ParkingLot parkingLot : parkingLot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getVehicleDetails().getVehicle().equals(vehicle)) {
                    int slot = entry.getKey();
                    return "Lot :" + lot + "," + "Slot :" + slot;
                }
            }
        }
        return null;
    }

    public ParkingLot getSlotForLargeVehicle(List<ParkingLot> parkingLots) {
        List<ParkingLot> parkingLotList = new ArrayList<>(parkingLots);
        parkingLotList.sort(Comparator.comparing(ParkingLot::getTotalNumberOfVehicleParked));
        return parkingLotList.get(0);
    }

    public ParkingLot getNearestFreeSpace(List<ParkingLot> parkingLots) {
        return parkingLots.stream().filter(parkingLot -> parkingLot.parkingSlots.entrySet()
                .stream().anyMatch(entry -> entry.getValue() == null)).findFirst().orElse(null);
    }

    public List<String> findLocationOfWhiteVehicle(VehicleColor color) {
        ArrayList<String> allWhiteVehicleLocation = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
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
        for (ParkingLot parkingLot : parkingLot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
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
        for (ParkingLot parkingLot : parkingLot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
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
        for (ParkingLot parkingLot : parkingLot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
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
        for (ParkingLot parkingLot : parkingLot) {
            lot++;
            for (Map.Entry<Integer, SlotDetails> entry : parkingLot.parkingSlots.entrySet()) {
                if (entry.getValue() != null && lot == findLot && entry.getValue().getVehicleDetails().getDriverCategory().equals(driverCategory)) {
                    int slot = entry.getKey();
                    String location = "Lot :" + lot + "," + "Slot :" + slot;
                    allHandicappedDriverVehicleLocation.add(location);
                }
            }
        }
        return allHandicappedDriverVehicleLocation;
    }

    public ArrayList<String> getAllVehiclePlateDetails() {
        return parkingLot.stream()
                .flatMap(parkingLot1 -> parkingLot1.parkingSlots.entrySet().stream())
                .filter(entry -> entry.getValue() != null)
                .map(entry -> "Plate Number = " + entry.getValue().getVehicleDetails().getVehicle())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
