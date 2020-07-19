package com.bridgelabz.parkinglot.test;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.ParkingLotOwner;
import com.bridgelabz.parkinglot.model.SecurityStaff;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.bridgelabz.parkinglot.service.ParkingLotSystem.PARK_LOT_SIZE;

public class ParkingLotTest {
    @Test
    public void givenParkingLotOwner_WhenOwnerWantDriverAbleToParkTheirCar_SoTheyCanCatchTheirFlight()
            throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.park(1001, 101);
        boolean parkingStatus = parkingLotSystem.isPark(101);
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        Boolean catchFlight = parkingLotOwner.getFlight(parkingStatus);
        Assert.assertTrue(catchFlight);
    }

    @Test
    public void givenParkingLotOwner_WhenOwnerWantDriverAbleToParkButDriverDidNotParked_SoTheyCanNotCatchTheirFlight() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
            boolean parkingStatus = parkingLotSystem.isPark();
            ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
            Boolean catchFlight = parkingLotOwner.getFlight(parkingStatus);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.ENTER_INPUT, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenDriver_WhenUnParkCar_SoThatCanGoHome() throws ParkingLotException {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
            parkingLotSystem.park(1001, 101);
            parkingLotSystem.park(1002, 102);
            boolean isParked = parkingLotSystem.isPark(101);
            Assert.assertTrue(isParked);
            parkingLotSystem.unPark(1001);
            boolean isUnParked = parkingLotSystem.isUnPark(101);
            Assert.assertFalse(isUnParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.EMPTY_PARKING_LOT, e.type);
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void givenParkingLotOwner_WhenLotFull_SoThatOwnerCanPutFullSign() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
            parkingLotSystem.park(1, 11);
            parkingLotSystem.park(2, 12);
            parkingLotSystem.park(3, 13);
            parkingLotSystem.park(4, 14);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_SIZE_EXCEEDED, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenAirportSecurityPersonal_WhenLotFull_SoThatRedirectSecurityStaff() {
        try {

            ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
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
    public void givenAirportSecurityPersonal_WhenLotNotFull_SoThatNotRedirectSecurityStaff() {
        try {

            ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
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
    public void givenParkingLotOwner_WhenLotHasSpaceAgain_SoThatOwnerCanTakeFullSign() {
        try {
            ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
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
}