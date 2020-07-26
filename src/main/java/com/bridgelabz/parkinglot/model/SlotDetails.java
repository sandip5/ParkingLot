package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;

public class SlotDetails {
    private final String attendantName;
    public ParkedVehicleDetails vehicle;
    private final LocalDateTime time;

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

    public LocalDateTime getTime() {
        return time;
    }
}