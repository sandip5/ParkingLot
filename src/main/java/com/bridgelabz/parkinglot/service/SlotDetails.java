package com.bridgelabz.parkinglot.service;

import java.time.LocalDateTime;
import java.util.Objects;

public class SlotDetails {
    private final Object vehicle;
    private final LocalDateTime time;

    public SlotDetails(Object vehicle, LocalDateTime time) {
        this.vehicle = vehicle;
        this.time = time;
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
    public int hashCode() {
        return Objects.hash(vehicle, time);
    }
}
