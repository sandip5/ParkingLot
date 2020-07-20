package com.bridgelabz.parkinglot.exception;

public class ParkingLotException extends Throwable {
    public enum ExceptionType {
        LOT_SIZE_FULL,
        DUPLICATE_ENTRY,
        NO_VEHICLE,
        NULL_VALUE,
        ZERO_VALUE,
        EMPTY_PARKING_LOT,
        ENTER_INPUT
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
