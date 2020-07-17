package com.bridgelabz.parkinglot.test;

import com.bridgelabz.parkinglot.model.Car;
import com.bridgelabz.parkinglot.model.Driver;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotOwner;
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
        Integer slotId = 101;
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
        Integer slotId = 101;
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
        Integer slotId = 101;
        ParkingLot parkingLot = new ParkingLot(driver, slotId);
        boolean parkingStatus = parkingLot.isPark(parkingLot);
        Assert.assertTrue(parkingStatus);
        ParkingLot unParkParkingLot = driver.unPark(parkingLot);
        Boolean parkingStatusAtUnParkTime = driver.goHome(unParkParkingLot);
        Assert.assertTrue(parkingStatusAtUnParkTime);
    }
}