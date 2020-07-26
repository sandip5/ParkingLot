package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;

public class SlotDetails {
    private final String attendantName;
    public ParkedVehicleDetails vehicle;
    private LocalDateTime time;

    public SlotDetails(ParkedVehicleDetails vehicle, LocalDateTime time, String attendantName) {
        this.vehicle = vehicle;
        this.time = time;
        this.attendantName = attendantName;
    }

    public ParkedVehicleDetails getVehicleDetails() {
        return vehicle;
    }

    public String getAttendantName() {
        return attendantName;
    }

    public ParkedVehicleDetails getVehicle() {
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