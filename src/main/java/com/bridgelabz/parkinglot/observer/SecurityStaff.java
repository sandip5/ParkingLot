package com.bridgelabz.parkinglot.observer;

public class SecurityStaff implements IParkingLotSystemObserver {
    private boolean isFullCapacity;

    @Override
    public void capacityIsFull() {
        this.isFullCapacity = true;
    }

    @Override
    public void capacityIsAvailable() {
        this.isFullCapacity = false;
    }

    public boolean isFullCapacity() {
        return isFullCapacity;
    }
}
