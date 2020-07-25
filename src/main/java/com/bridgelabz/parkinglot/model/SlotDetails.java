package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;

public class SlotDetails {
    public Object vehicle;
    private LocalDateTime time;

    public SlotDetails(Object vehicle, LocalDateTime time) {
        this.vehicle = vehicle;
        this.time = time;
    }

    public Object getVehicle() {
        return vehicle;
    }

    public LocalDateTime getTime() {
        return time;
    }
}