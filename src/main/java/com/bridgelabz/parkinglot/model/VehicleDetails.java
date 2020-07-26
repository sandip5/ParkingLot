package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleSize;

public class VehicleDetails {

    private final Object vehicle;
    private final DriverCategory driverCategory;
    private final VehicleSize vehicleSize;

    public VehicleColor getColor() {
        return color;
    }

    private VehicleColor color;

    public VehicleDetails(Object vehicle, DriverCategory driverCategory, VehicleSize vehicleSize) {
        this.vehicle = vehicle;
        this.driverCategory = driverCategory;
        this.vehicleSize = vehicleSize;
    }

    public VehicleDetails(Object vehicle, DriverCategory driverCategory, VehicleSize vehicleSize, VehicleColor color) {
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
