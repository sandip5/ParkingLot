package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;

public class SlotDetails {
    public VehicleDetails vehicle;
    private LocalDateTime time;

    public SlotDetails(VehicleDetails vehicle, LocalDateTime time) {
        this.vehicle = vehicle;
        this.time = time;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicle;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "SlotDetails{" +
                "vehicle=" + vehicle +
                ", time=" + time +
                '}';
    }
}