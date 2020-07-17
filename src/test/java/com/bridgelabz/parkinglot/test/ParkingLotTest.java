package com.bridgelabz.parkinglot.test;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Car;
import com.bridgelabz.parkinglot.model.Driver;
import com.bridgelabz.parkinglot.model.ParkingLotOwner;
import com.bridgelabz.parkinglot.model.SecurityStaff;
import com.bridgelabz.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
    @Test
    public void welcomeTestCase() {
        System.out.println("Welcome To Parking Lot Problem");
    }

    @Test
    public void givenParkingLotOwner_WhenOwnerWantDriverAbleToParkTheirCar_SoTheyCanCatchTheirFlight() {
        Integer carId = 1001;
        Car car = new Car(carId);
        Driver driver = new Driver(car);
        int slotId = 101;
        ParkingLot parkingLot = new ParkingLot(driver, slotId);
        boolean parkingStatus = parkingLot.isPark(parkingLot);
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        Boolean catchFlight = parkingLotOwner.getFlight(parkingStatus);
        Assert.assertTrue(catchFlight);
    }

    @Test
    public void givenParkingLotOwner_WhenOwnerWantDriverAbleToParkButDriverDidNotParked_SoTheyCanNotCatchTheirFlight() {
        Integer carId = null;
        Car car = new Car(carId);
        Driver driver = new Driver(car);
        int slotId = 101;
        ParkingLot parkingLot = new ParkingLot(driver, slotId);
        boolean parkingStatus = parkingLot.isPark(parkingLot);
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        Boolean catchFlight = parkingLotOwner.getFlight(parkingStatus);
        Assert.assertFalse(catchFlight);
    }

    @Test
    public void givenDriver_WhenUnParkCar_SoThatCanGoHome() {
        Integer carId = 1001;
        Car car = new Car(carId);
        Driver driver = new Driver(car);
        int slotId = 101;
        ParkingLot parkingLot = new ParkingLot(driver, slotId);
        boolean parkingStatus = parkingLot.isPark(parkingLot);
        Assert.assertTrue(parkingStatus);
        ParkingLot unParkParkingLot = driver.unPark();
        Boolean parkingStatusAtUnParkTime = driver.goHome(unParkParkingLot);
        Assert.assertTrue(parkingStatusAtUnParkTime);
    }

    @Test
    public void givenParkingLotOwner_WhenLotFullAndTryToAddMoreCar_ShouldThrowException() {
        try {
            Integer carId1 = 1001;
            Car alto = new Car(carId1);
            Driver driver1 = new Driver(alto);
            int slotId1 = 101;
            ParkingLot parkingLot1 = new ParkingLot(driver1, slotId1);
            Integer carId2 = 1001;
            Car xuv = new Car(carId2);
            Driver driver2 = new Driver(xuv);
            int slotId2 = 102;
            ParkingLot parkingLot2 = new ParkingLot(driver2, slotId2);
            Integer carId3 = 1001;
            Car bmw = new Car(carId3);
            Driver driver3 = new Driver(bmw);
            int slotId3 = 102;
            ParkingLot parkingLot3 = new ParkingLot(driver3, slotId3);
            Integer carId4 = 1001;
            Car jaguar = new Car(carId4);
            Driver driver4 = new Driver(jaguar);
            int slotId4 = 102;
            ParkingLot parkingLot4 = new ParkingLot(driver4, slotId4);
            ParkingLot[] lotSpace = new ParkingLot().fillLot(parkingLot1, parkingLot2, parkingLot3, parkingLot4);
            boolean checkFullSign = new ParkingLot().checkLot(lotSpace);
            Assert.assertTrue(checkFullSign);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenParkingLotOwner_WhenLotFull_SoThatOwnerCanPutFullSign() {
        try {
            Integer carId1 = 1001;
            Car alto = new Car(carId1);
            Driver driver1 = new Driver(alto);
            int slotId1 = 101;
            ParkingLot parkingLot1 = new ParkingLot(driver1, slotId1);
            Integer carId2 = 1001;
            Car xuv = new Car(carId2);
            Driver driver2 = new Driver(xuv);
            int slotId2 = 102;
            ParkingLot parkingLot2 = new ParkingLot(driver2, slotId2);
            Integer carId3 = 1001;
            Car bmw = new Car(carId3);
            Driver driver3 = new Driver(bmw);
            int slotId3 = 102;
            ParkingLot parkingLot3 = new ParkingLot(driver3, slotId3);
            ParkingLot[] lotSpace = new ParkingLot().fillLot(parkingLot1, parkingLot2, parkingLot3);
            boolean checkFullSign = new ParkingLot().checkLot(lotSpace);
            Assert.assertTrue(checkFullSign);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenAirportSecurityPersonal_WhenLotFull_SoThatRedirectSecurityStaff() {
        try {
            Integer carId1 = 1001;
            Car alto = new Car(carId1);
            Driver driver1 = new Driver(alto);
            int slotId1 = 101;
            ParkingLot parkingLot1 = new ParkingLot(driver1, slotId1);
            Integer carId2 = 1001;
            Car xuv = new Car(carId2);
            Driver driver2 = new Driver(xuv);
            int slotId2 = 102;
            ParkingLot parkingLot2 = new ParkingLot(driver2, slotId2);
            Integer carId3 = 1001;
            Car bmw = new Car(carId3);
            Driver driver3 = new Driver(bmw);
            int slotId3 = 102;
            ParkingLot parkingLot3 = new ParkingLot(driver3, slotId3);
            ParkingLot[] lotSpace = new ParkingLot().fillLot(parkingLot1, parkingLot2, parkingLot3);
            boolean checkFullSign = new ParkingLot().checkLot(lotSpace);
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
            Integer carId1 = 1001;
            Car alto = new Car(carId1);
            Driver driver1 = new Driver(alto);
            int slotId1 = 101;
            ParkingLot parkingLot1 = new ParkingLot(driver1, slotId1);
            Integer carId2 = 1001;
            Car xuv = new Car(carId2);
            Driver driver2 = new Driver(xuv);
            int slotId2 = 102;
            ParkingLot parkingLot2 = new ParkingLot(driver2, slotId2);
            ParkingLot[] lotSpace = new ParkingLot().fillLot(parkingLot1, parkingLot2);
            boolean checkFullSign = new ParkingLot().checkLot(lotSpace);
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
            Integer carId1 = 1001;
            Car alto = new Car(carId1);
            Driver driver1 = new Driver(alto);
            int slotId1 = 101;
            ParkingLot parkingLot1 = new ParkingLot(driver1, slotId1);
            Integer carId2 = 1001;
            Car xuv = new Car(carId2);
            Driver driver2 = new Driver(xuv);
            int slotId2 = 102;
            ParkingLot parkingLot2 = new ParkingLot(driver2, slotId2);
            Integer carId3 = 1001;
            Car bmw = new Car(carId3);
            Driver driver3 = new Driver(bmw);
            int slotId3 = 102;
            ParkingLot parkingLot3 = new ParkingLot(driver3, slotId3);
            ParkingLot[] lotSpace = new ParkingLot().fillLot(parkingLot1, parkingLot2, parkingLot3);
            boolean checkFullSign = new ParkingLot().checkLot(lotSpace);
            String takeFullSign = new ParkingLot().freeSpace(lotSpace);
            Assert.assertEquals("Take Full Sign", takeFullSign);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}