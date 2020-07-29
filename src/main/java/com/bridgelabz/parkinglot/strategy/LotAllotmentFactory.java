package com.bridgelabz.parkinglot.strategy;

import com.bridgelabz.parkinglot.enums.DriverCategory;
import com.bridgelabz.parkinglot.enums.VehicleSize;
import com.bridgelabz.parkinglot.model.ParkedVehicleDetails;

public class LotAllotmentFactory {

    public ILotAllotmentStrategy getLotAllotmentStrategy(ParkedVehicleDetails parkedVehicleDetails) {

        if (parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL)
            return new NormalAndSmallCarLotAllotment();
        if (parkedVehicleDetails.getDriverCategory() == DriverCategory.HANDICAPPED &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.SMALL)
            return new HandicappedDriverCarLotAllotment();
        return parkedVehicleDetails.getDriverCategory() == DriverCategory.NORMAL &&
                parkedVehicleDetails.getVehicleSize() == VehicleSize.LARGE ? new LargeCarLotAllotment() : null;
    }
}
