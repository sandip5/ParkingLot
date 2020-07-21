package com.bridgelabz.parkinglot.test;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.SecurityStaff;
import com.bridgelabz.parkinglot.service.Attendant;
import com.bridgelabz.parkinglot.service.ParkingLotOwner;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.bridgelabz.parkinglot.service.ParkingLotSystem.PARK_LOT_SIZE;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem();
    }

    @Test
    public void welcomeTestCase() {
        System.out.println("Welcome To Parking Lot Problem");
    }

    @Test
    public void givenParkingLotSystem_WhenDriverParkedCar_ShouldReturnParkingStatusTrue()
            throws ParkingLotException {
        parkingLotSystem.park(1001, 101);
        boolean parkingStatus = parkingLotSystem.isPark(101);
        Assert.assertTrue(parkingStatus);
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
            parkingLotSystem.park(1001, 101);
            parkingLotSystem.park(1002, 102);
            boolean isParked = parkingLotSystem.isPark(101);
            Assert.assertTrue(isParked);
            parkingLotSystem.unPark(1001);
            boolean isUnParked = parkingLotSystem.isUnPark(101);
            Assert.assertFalse(isUnParked);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenLotFull_ShouldThrowException() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(2, 12);
            parkingLotSystem.park(3, 13);
            parkingLotSystem.park(4, 14);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_SIZE_FULL, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotFull_SoThatOwnerCanPutFullSign() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(2, 12);
            parkingLotSystem.park(3, 13);
            ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
            String parkingLotStatus = parkingLotOwner.getStatus(PARK_LOT_SIZE, parkingLotSystem);
            Assert.assertEquals("Parking Lot Full", parkingLotStatus);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotFull_SoThatRedirectSecurityStaff() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(2, 12);
            parkingLotSystem.park(3, 13);
            boolean checkFullSign = parkingLotSystem.checkLot(PARK_LOT_SIZE);
            SecurityStaff security = new SecurityStaff();
            String redirectMessage = security.redirect(checkFullSign);
            Assert.assertEquals("Lot Is Full, So Redirect Security", redirectMessage);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotNotFull_SoThatNotRedirectSecurityStaff() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(2, 12);
            boolean checkFullSign = parkingLotSystem.checkLot(PARK_LOT_SIZE);
            SecurityStaff security = new SecurityStaff();
            String redirectMessage = security.redirect(checkFullSign);
            Assert.assertEquals("Lot Is Not Full", redirectMessage);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenLotHasSpaceAgain_SoThatOwnerCanTakeFullSign() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(2, 12);
            parkingLotSystem.park(3, 13);
            parkingLotSystem.unPark(3);
            String takeFullSign = new ParkingLotSystem().freeSpace(PARK_LOT_SIZE);
            Assert.assertEquals("Take Full Sign", takeFullSign);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenTicketNumberZero_ShouldThrowException() {
        try {
            parkingLotSystem.park(0, 101);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ZERO_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleHaveZero_ShouldThrowException() {
        try {
            parkingLotSystem.park(10, 0);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ZERO_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleAndTicketHaveZero_ShouldThrowException() {
        try {
            parkingLotSystem.park(0, 0);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ZERO_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenTicketNumberNull_ShouldThrowException() {
        try {
            parkingLotSystem.park(null, 101);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NULL_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleHaveNull_ShouldThrowException() {
        try {
            parkingLotSystem.park(10, null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NULL_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenVehicleAndTicketHaveNul_ShouldThrowException() {
        try {
            parkingLotSystem.park(0, 0);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ZERO_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenIsParkEnteredZero_ShouldThrowException() {
        try {
            parkingLotSystem.park(1001, 101);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ZERO_VALUE, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenIsParkEnteredNull_ShouldThrowException() {
        try {
            parkingLotSystem.park(1001, 101);
            parkingLotSystem.isPark(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenEnterDuplicatesForVehicle_ShouldThrowException() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(2, 11);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.DUPLICATE_ENTRY, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenEnterDuplicatesForTicket_ShouldThrowException() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(1, 12);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.DUPLICATE_ENTRY, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotSystem_WhenEnterDuplicatesForTicketAndVehicle_ShouldThrowException() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(1, 11);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.DUPLICATE_ENTRY, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenEnterWrongDetailsInIsPark_ShouldThrowException() {
        try {
            parkingLotSystem.park(1, 11);
            parkingLotSystem.isPark(12);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenUnParkAndNoVehicleLeft_ShouldThrowException() {
        try {
            parkingLotSystem.park(1001, 101);
            boolean isParked = parkingLotSystem.isPark(101);
            Assert.assertTrue(isParked);
            parkingLotSystem.unPark(1001);
            parkingLotSystem.isUnPark(101);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.EMPTY_PARKING_LOT, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotSystem_WhenParkingAttendantParkCar_ShouldMakeDecisionWhereToParkCar() {
        try{
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3);
            Attendant attendant = new Attendant();
            int slotNo = attendant.whereToPark(parkingLotSystem);
            parkingLotSystem.park(attendant.whereToPark(parkingLotSystem), 11);
            int slotNo1 = attendant.whereToPark(parkingLotSystem);
            parkingLotSystem.park(attendant.whereToPark(parkingLotSystem), 12);
            parkingLotSystem.park(attendant.whereToPark(parkingLotSystem), 12);
            parkingLotSystem.unPark(slotNo1);
            parkingLotSystem.park(attendant.whereToPark(parkingLotSystem), 15);
            Assert.assertEquals(1, slotNo);
        } catch (ParkingLotException e){
            System.out.println(e.getMessage());
        }

    }
}