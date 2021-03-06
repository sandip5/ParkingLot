package com.bridgelabz.parkinglot.test;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleManufacturerName;
import com.bridgelabz.parkinglot.enums.VehicleSize;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;
import com.bridgelabz.parkinglot.observer.ParkingLotOwner;
import com.bridgelabz.parkinglot.observer.SecurityStaff;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(3,3);
    }

    @Test
    public void welcomeTestCase() {
        System.out.println("Welcome To Parking Lot Problem");
    }

    @Test
    public void givenParkingLotSystem_WhenDriverParkedCar_ShouldReturnParkingStatusTrue() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(101, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            boolean parkingStatus = parkingLotSystem.isPark(101);
            Assert.assertTrue(parkingStatus);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenDriverDidNotParkedAndParkingAskedThatParkedOrNot_ShouldThrowException() {
        try {
            parkingLotSystem.isPark();
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ENTER_INPUT, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenUnParkCar_ShouldReturnFalse() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(101, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(102, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            boolean isParked = parkingLotSystem.isPark(101);
            Assert.assertTrue(isParked);
            boolean isUnParked = parkingLotSystem.unPark(101);
            Assert.assertTrue(isUnParked);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenLotFull_ShouldThrowException() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 3);
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(14, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_SIZE_FULL, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotFull_ShouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 2);
            parkingLotSystem.registerListener(parkingLotOwner);
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(14, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(15, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
        } catch (ParkingLotException e) {
            Assert.assertTrue(parkingLotOwner.isFullCapacity());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotNotFull_ShouldReturnFalse() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 2);
            parkingLotSystem.registerListener(parkingLotOwner);
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(14, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.unPark(14);
            Assert.assertFalse(parkingLotOwner.isFullCapacity());
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenSecurityStaff_WhenLotFull_ShouldReturnTrue() {
        SecurityStaff securityStaff = new SecurityStaff();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 2);
            parkingLotSystem.registerListener(securityStaff);
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(14, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(15, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
        } catch (ParkingLotException e) {
            Assert.assertTrue(securityStaff.isFullCapacity());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenSecurityStaff_WhenLotNotFull_ShouldReturnFalse() {
        SecurityStaff securityStaff = new SecurityStaff();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 2);
            parkingLotSystem.registerListener(securityStaff);
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(14, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.unPark(14);
            Assert.assertFalse(securityStaff.isFullCapacity());
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleHaveZero_ShouldThrowException() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(0, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ZERO_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleHaveNull_ShouldThrowException() {
        try {
            parkingLotSystem.park(null, "Ashish");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NULL_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenEnterDuplicatesForVehicle_ShouldThrowException() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.DUPLICATE_ENTRY, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenEnterWrongDetailsInIsPark_ShouldThrowException() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            Assert.assertFalse(parkingLotSystem.isPark(12));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenUnParkAndNoVehicleLeft_ShouldThrowException() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(101, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            boolean isParked = parkingLotSystem.isPark(101);
            Assert.assertTrue(isParked);
            Assert.assertFalse(parkingLotSystem.unPark(1001));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenParkCar_ShouldReturnSlotNo() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 2);
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(14, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            Assert.assertEquals("Lot :2,Slot :2", parkingLotSystem.getVehicleLocation(14));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenFindVehicleUsingVehicleDetails_ShouldReturnSlot() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            Object slotNo = parkingLotSystem.getVehicleLocation(12);
            Assert.assertEquals("Lot :2,Slot :1", slotNo);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotOwnerWantToKnow_WhenCarWasParkedOnMyLot_ShouldReturnParkedTime() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(11, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(12, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(13, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            LocalDateTime time = parkingLotSystem.getParkingTime(11);
            Assert.assertEquals(LocalDateTime.now().withNano(0), time);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldParkedInEvenDistribution() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 2);
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            ParkedVehicleDetails parkedVehicleDetails = new ParkedVehicleDetails(4, DriverCategory.NORMAL,
                    VehicleSize.SMALL);
            parkingLotSystem.park(parkedVehicleDetails, "Ashish");
            String vehicleLocation = parkingLotSystem.getVehicleLocation(4);
            Assert.assertEquals("Lot :2,Slot :2", vehicleLocation);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParkedThenUnParked_ShouldReturnLocationOfVehicleNull() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 3);
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.unPark(3);
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            Assert.assertNull(parkingLotSystem.getVehicleLocation(3));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParkedThenUnParkedAfterThatVehicleParked_ShouldReturnLocationOfVehicle() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 2);
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.unPark(3);
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            Assert.assertEquals("Lot :1,Slot :2", parkingLotSystem.getVehicleLocation(4));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenHandicapDriver_WhenParkedVehicle_ParkedVehicleNearestFreeSpace() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 3);
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.unPark(2);
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            ParkedVehicleDetails parkedVehicleDetails = new ParkedVehicleDetails(4, DriverCategory.HANDICAPPED,
                    VehicleSize.SMALL);
            parkingLotSystem.park(parkedVehicleDetails, "Ashish");
            String vehicleLocation = parkingLotSystem.getVehicleLocation(4);
            Assert.assertEquals("Lot :1,Slot :2", vehicleLocation);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenLargeVehicle_WhenParked_ShouldParkedInMostNumberOfFreeSlots() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.LARGE),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(5, DriverCategory.NORMAL, VehicleSize.LARGE),
                    "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(6, DriverCategory.NORMAL, VehicleSize.SMALL),
                    "Ashish");
            ParkedVehicleDetails parkedVehicleDetails = new ParkedVehicleDetails(7, DriverCategory.NORMAL,
                    VehicleSize.LARGE);
            parkingLotSystem.park(parkedVehicleDetails, "Ashish");
            String vehicleLocation = parkingLotSystem.getVehicleLocation(5);
            Assert.assertEquals("Lot :2,Slot :2", vehicleLocation);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenWhiteVehicle_WhenParked_ShouldReturnAllWhiteVehicleLocation() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.NO_COLOR), "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.WHITE), "Ashish");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.WHITE), "Ashish");
            List<String> locationOfSecondWhiteVehicle = parkingLotSystem.findLocationOfVehicleAsPerColor(VehicleColor.WHITE);
            List<String> expected = Arrays.asList("Lot :2,Slot :1", "Lot :3,Slot :1");
            Assert.assertEquals(expected, locationOfSecondWhiteVehicle);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenBlueToyotaVehicle_WhenAttendantParked_ShouldGiveBlueToyotaVehicle() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.NO_COLOR, VehicleManufacturerName.TOYOTA), "Galaxy");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Moon");
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Sky");
            List<String> locationOfBlueToyotaVehicle = parkingLotSystem
                    .findLocationOfVehicleAsPerColorAndCompanyName(VehicleColor.BLUE, VehicleManufacturerName.TOYOTA);
            List<String> expected = Arrays.asList("Lot :1,Slot :2,Plate Number :4,Parking Attendant :Sky",
                    "Lot :3,Slot :1,Plate Number :3,Parking Attendant :Moon");
            Assert.assertEquals(expected, locationOfBlueToyotaVehicle);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenBmwVehicle_WhenAttendantParked_ShouldGiveLocationOfBmwVehicle() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.NO_COLOR, VehicleManufacturerName.BMW), "Galaxy");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.BMW), "Moon");
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Sky");
            List<String> locationOfBmwVehicle = parkingLotSystem.findLocationOfVehicleAsPerCompanyName(VehicleManufacturerName.BMW);
            List<String> expected = Arrays.asList("Lot :1,Slot :1", "Lot :3,Slot :1");
            Assert.assertEquals(expected, locationOfBmwVehicle);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnVehicleThatHaveBeenParkedLastThirtyMinutes() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.NO_COLOR, VehicleManufacturerName.BMW), "Galaxy");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.BMW), "Moon");
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Sky");
            List<String> locationOfVehicleWhichParkedAsPerLastTime = parkingLotSystem.findLocationOfVehicleAsPerTime(30);
            List<String> expected = Arrays.asList("Lot :1,Slot :1", "Lot :1,Slot :2", "Lot :2,Slot :1", "Lot :3,Slot :1");
            Assert.assertEquals(expected, locationOfVehicleWhichParkedAsPerLastTime);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenHandicapDriverParkedCarInSpecificLot_ShouldReturnThatSpecificLot() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.NO_COLOR, VehicleManufacturerName.BMW), "Galaxy");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.BMW), "Moon");
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Sky");
            parkingLotSystem.park(new ParkedVehicleDetails(5, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(6, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.BMW), "Moon");
            parkingLotSystem.park(new ParkedVehicleDetails(7, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Sky");
            parkingLotSystem.park(new ParkedVehicleDetails(8, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(9, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            List<String> locationOfVehicleWhichParkedInSpecificLot = parkingLotSystem.
                    findLocationOfVehicleAsPerLotAndDriverCategory(DriverCategory.HANDICAPPED, 2);
            List<String> expected = Collections.singletonList("Lot :2,Slot :3");
            Assert.assertEquals(expected, locationOfVehicleWhichParkedInSpecificLot);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkedInParking_ShouldReturnPlateNumberOfAllVehicles() {
        try {
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.NO_COLOR, VehicleManufacturerName.BMW), "Galaxy");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(3, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.BMW), "Moon");
            parkingLotSystem.park(new ParkedVehicleDetails(4, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Sky");
            parkingLotSystem.park(new ParkedVehicleDetails(5, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(6, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.BMW), "Moon");
            parkingLotSystem.park(new ParkedVehicleDetails(7, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.BLUE, VehicleManufacturerName.TOYOTA), "Sky");
            parkingLotSystem.park(new ParkedVehicleDetails(8, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            parkingLotSystem.park(new ParkedVehicleDetails(9, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
            List<String> locationOfSecondWhiteVehicle = parkingLotSystem.
                    getAllVehiclePlateDetails();
            List<String> expected = Arrays.asList("Plate Number = 1", "Plate Number = 2", "Plate Number = 5",
                    "Plate Number = 3", "Plate Number = 6", "Plate Number = 8", "Plate Number = 4", "Plate Number = 7",
                    "Plate Number = 9");
            Assert.assertEquals(expected, locationOfSecondWhiteVehicle);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkedAndParkingLotFull_ShouldThrowException() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(1, 1);
            parkingLotSystem.park(new ParkedVehicleDetails(1, DriverCategory.NORMAL, VehicleSize.SMALL,
                    VehicleColor.NO_COLOR, VehicleManufacturerName.BMW), "Galaxy");
            parkingLotSystem.park(new ParkedVehicleDetails(2, DriverCategory.HANDICAPPED, VehicleSize.SMALL,
                    VehicleColor.WHITE, VehicleManufacturerName.TOYOTA), "Sun");
        }catch (ParkingLotException e){
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_SIZE_FULL, e.type);
        }
    }
}