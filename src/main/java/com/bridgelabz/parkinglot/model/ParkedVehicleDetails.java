package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleManufacturerName;
import com.bridgelabz.parkinglot.enums.VehicleSize;

public class ParkedVehicleDetails {

    private final Object vehicle;
    private final DriverCategory driverCategory;
    private final VehicleSize vehicleSize;
    private VehicleManufacturerName vehicleManufacturerName;

    public VehicleManufacturerName getVehicleManufacturerName() {
        return vehicleManufacturerName;
    }

    public ParkedVehicleDetails(Object vehicle, DriverCategory driverCategory, VehicleSize vehicleSize,
                                VehicleColor color, VehicleManufacturerName vehicleManufacturerName) {
        this.vehicle = vehicle;
        this.driverCategory = driverCategory;
        this.vehicleSize = vehicleSize;
        this.color = color;
        this.vehicleManufacturerName = vehicleManufacturerName;
    }

    public VehicleColor getColor() {
        return color;
    }

    private VehicleColor color;

    public ParkedVehicleDetails(Object vehicle, DriverCategory driverCategory, VehicleSize vehicleSize) {
        this.vehicle = vehicle;
        this.driverCategory = driverCategory;
        this.vehicleSize = vehicleSize;
    }

    public ParkedVehicleDetails(Object vehicle, DriverCategory driverCategory, VehicleSize vehicleSize, VehicleColor color) {
        this.vehicle = vehicle;
        this.driverCategory = driverCategory;
        this.vehicleSize = vehicleSize;
        this.color = color;
    }

    public Object getVehicle() {
        return vehicle;
    }

    public DriverCategory getDriverCategory() {
        return driverCategory;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }
}
