package com.bridgelabz.parkinglot.test;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.ParkingLotOwner;
import com.bridgelabz.parkinglot.observer.SecurityStaff;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(3);
    }

    @Test
    public void welcomeTestCase() {
        System.out.println("Welcome To Parking Lot Problem");
    }

    @Test
    public void givenParkingLotSystem_WhenDriverParkedCar_ShouldReturnParkingStatusTrue() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 101);
            boolean parkingStatus = parkingLotSystem.isPark(101);
            Assert.assertTrue(parkingStatus);
        } catch (ParkingLotException e){
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
            parkingLotSystem.park(DriverCategory.NORMAL, 101);
            parkingLotSystem.park(DriverCategory.NORMAL, 102);
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
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            parkingLotSystem.park(DriverCategory.NORMAL, 14);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_SIZE_FULL, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotFull_ShouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
            parkingLotSystem.registerListener(parkingLotOwner);
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            parkingLotSystem.park(DriverCategory.NORMAL, 14);
        } catch (ParkingLotException e) {
            Assert.assertTrue(parkingLotOwner.isFullCapacity());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotNotFull_ShouldReturnFalse() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
            parkingLotSystem.registerListener(parkingLotOwner);
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            parkingLotSystem.park(DriverCategory.NORMAL, 14);
        } catch (ParkingLotException e) {
            parkingLotSystem.unPark(14);
            Assert.assertFalse(parkingLotOwner.isFullCapacity());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenSecurityStaff_WhenLotFull_ShouldReturnTrue() {
        SecurityStaff securityStaff = new SecurityStaff();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
            parkingLotSystem.registerListener(securityStaff);
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            parkingLotSystem.park(DriverCategory.NORMAL, 14);
        } catch (ParkingLotException e) {
            Assert.assertTrue(securityStaff.isFullCapacity());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenSecurityStaff_WhenLotNotFull_ShouldReturnFalse() {
        SecurityStaff securityStaff = new SecurityStaff();
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
            parkingLotSystem.registerListener(securityStaff);
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            parkingLotSystem.park(DriverCategory.NORMAL, 14);
        } catch (ParkingLotException e) {
            parkingLotSystem.unPark(14);
            Assert.assertFalse(securityStaff.isFullCapacity());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleHaveZero_ShouldThrowException() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 0);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ZERO_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleHaveNull_ShouldThrowException() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NULL_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenEnterDuplicatesForVehicle_ShouldThrowException() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.DUPLICATE_ENTRY, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenEnterWrongDetailsInIsPark_ShouldThrowException() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            Assert.assertFalse(parkingLotSystem.isPark(12));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenUnParkAndNoVehicleLeft_ShouldThrowException() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 101);
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
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            parkingLotSystem.park(DriverCategory.NORMAL, 14);
            Assert.assertEquals("Lot :1,Slot :2", parkingLotSystem.getVehicleLocation(14));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenFindVehicleUsingVehicleDetails_ShouldReturnSlot() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            Object slotNo = parkingLotSystem.getVehicleLocation(12);
            Assert.assertEquals("Lot :2,Slot :1", slotNo);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotOwnerWantToKnow_WhenCarWasParkedOnMyLot_ShouldReturnParkedTime() {
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 11);
            parkingLotSystem.park(DriverCategory.NORMAL, 12);
            parkingLotSystem.park(DriverCategory.NORMAL, 13);
            LocalDateTime time = parkingLotSystem.getParkingTime(11);
            Assert.assertEquals(LocalDateTime.now().withNano(0), time);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldParkedInEvenDistribution() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 1);
            parkingLotSystem.park(DriverCategory.NORMAL, 2);
            parkingLotSystem.park(DriverCategory.NORMAL, 3);
            parkingLotSystem.park(DriverCategory.NORMAL, 4);
            Assert.assertEquals("Lot :1,Slot :1", parkingLotSystem.getVehicleLocation(1));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParkedThenUnParked_ShouldReturnLocationOfVehicleNull() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 1);
            parkingLotSystem.park(DriverCategory.NORMAL, 2);
            parkingLotSystem.park(DriverCategory.NORMAL, 3);
            parkingLotSystem.unPark(3);
            parkingLotSystem.park(DriverCategory.NORMAL, 4);
            Assert.assertNull(parkingLotSystem.getVehicleLocation(3));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParkedThenUnParkedAfterThatVehicleParked_ShouldReturnLocationOfVehicle() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 1);
            parkingLotSystem.park(DriverCategory.NORMAL, 2);
            parkingLotSystem.park(DriverCategory.NORMAL, 3);
            parkingLotSystem.unPark(3);
            parkingLotSystem.park(DriverCategory.NORMAL, 4);
            Assert.assertEquals("Lot :1,Slot :2", parkingLotSystem.getVehicleLocation(4));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenHandicapDriver_WhenParkedVehicle_ParkedVehicleNearestFreeSpace() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        try {
            parkingLotSystem.park(DriverCategory.NORMAL, 1);
            parkingLotSystem.park(DriverCategory.NORMAL, 2);
            parkingLotSystem.park(DriverCategory.HANDICAPPED, 3);
            parkingLotSystem.park(DriverCategory.HANDICAPPED, 4);
            Assert.assertEquals("Lot :2,Slot :2", parkingLotSystem.getVehicleLocation(4));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}