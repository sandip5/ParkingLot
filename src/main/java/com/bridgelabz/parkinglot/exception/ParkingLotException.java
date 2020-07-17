package com.bridgelabz.parkinglot.exception;

public class ParkingLotException extends Throwable {
    public enum ExceptionType {
        LOT_SIZE_EXCEEDED
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
