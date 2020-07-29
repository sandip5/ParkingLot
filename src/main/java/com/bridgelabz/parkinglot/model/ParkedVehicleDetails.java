package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleManufacturerName;
import com.bridgelabz.parkinglot.enums.VehicleSize;

public class ParkedVehicleDetails {
    private final Object plateNumber;
    private final DriverCategory driverCategory;
    private final VehicleSize vehicleSize;
    private VehicleManufacturerName vehicleManufacturerName;
    private VehicleColor color;

    public ParkedVehicleDetails(Object plateNumber, DriverCategory driverCategory, VehicleSize vehicleSize) {
        this.plateNumber = plateNumber;
        this.driverCategory = driverCategory;
        this.vehicleSize = vehicleSize;
    }

    public ParkedVehicleDetails(Object plateNumber, DriverCategory driverCategory, VehicleSize vehicleSize, VehicleColor color) {
        this.plateNumber = plateNumber;
        this.driverCategory = driverCategory;
        this.vehicleSize = vehicleSize;
        this.color = color;
    }

    public ParkedVehicleDetails(Object plateNumber, DriverCategory driverCategory, VehicleSize vehicleSize,
                                VehicleColor color, VehicleManufacturerName vehicleManufacturerName) {
        this.plateNumber = plateNumber;
        this.driverCategory = driverCategory;
        this.vehicleSize = vehicleSize;
        this.color = color;
        this.vehicleManufacturerName = vehicleManufacturerName;
    }

    public Object getPlateNumber() {
        return plateNumber;
    }

    public DriverCategory getDriverCategory() {
        return driverCategory;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public VehicleManufacturerName getVehicleManufacturerName() {
        return vehicleManufacturerName;
    }

    public VehicleColor getColor() {
        return color;
    }
}
