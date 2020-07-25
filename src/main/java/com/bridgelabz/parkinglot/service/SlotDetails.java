package com.bridgelabz.parkinglot.service;

import java.time.LocalDateTime;
import java.util.Objects;

public class SlotDetails {
    private Object vehicle;
    private LocalDateTime time;

    public SlotDetails(Object vehicle, LocalDateTime time) {
        this.vehicle = vehicle;
        this.time = time;
    }

    public SlotDetails() {
    }

    public Object getVehicle() {
        return vehicle;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotDetails that = (SlotDetails) o;
        return Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(time, that.time);
    }

    @Override
    public String toString() {
        return "SlotDetails{" +
                "vehicle=" + vehicle +
                ", time=" + time +
                '}';
    }
}