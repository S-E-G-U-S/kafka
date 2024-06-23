package com.jydev.taxi.application.model.mapper;

import com.jydev.taxi.application.model.GetTaxiCallModel;
import com.jydev.taxi.domain.TaxiCall;
import com.jydev.taxi.domain.TaxiRide;
import jakarta.annotation.Nullable;

public class TaxiCallConvertor {

    public static GetTaxiCallModel convert(TaxiCall taxiCall, @Nullable TaxiRide taxiRide) {


        GetTaxiCallModel.TaxiRideInfo rideInfo = null;

        boolean taxiMatched = taxiRide != null;

        if (taxiMatched) {
            var driver = taxiRide.getTaxiDriver();

            rideInfo = new GetTaxiCallModel.TaxiRideInfo(
                    taxiCall.getDriverId(),
                    taxiCall.getId(),
                    driver.getDriverName(),
                    driver.getDriverPhoneNumber(),
                    driver.getVehicleNumber()
            );
        }


        return new GetTaxiCallModel(
                taxiCall.getStatus(),
                rideInfo
        );
    }
}
