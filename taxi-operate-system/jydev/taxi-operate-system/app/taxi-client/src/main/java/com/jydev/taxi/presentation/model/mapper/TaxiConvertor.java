package com.jydev.taxi.presentation.model.mapper;

import com.jydev.taxi.application.model.GetTaxiCallModel;
import com.jydev.taxi.application.model.GetTaxiRideModel;
import com.jydev.taxi.presentation.model.response.GetTaxiCallResponse;
import com.jydev.taxi.presentation.model.response.GetTaxiRideResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TaxiConvertor {

    public static GetTaxiCallResponse convert(GetTaxiCallModel model) {

        GetTaxiCallResponse.TaxiRideInfo rideInfo = null;

        boolean taxiMatched = model.rideInfo() != null;

        if (taxiMatched) {
            var taxiRide = model.rideInfo();

            rideInfo = new GetTaxiCallResponse.TaxiRideInfo(
                    taxiRide.rideId(),
                    taxiRide.driverId(),
                    taxiRide.driverName(),
                    taxiRide.driverPhoneNumber(),
                    taxiRide.vehicleNumber()
            );
        }

        return new GetTaxiCallResponse(
                model.status(),
                rideInfo
        );
    }

    public static GetTaxiRideResponse convert(GetTaxiRideModel model, ZoneId zoneId) {

        GetTaxiRideResponse.TaxiDriverInfo driverInfo = new GetTaxiRideResponse.TaxiDriverInfo(
                model.driverId(),
                model.driverName(),
                model.driverPhoneNumber(),
                model.vehicleNumber()
        );


        LocalDateTime startTime = model.startTime()
                .map(time -> LocalDateTime.ofInstant(time, zoneId))
                .orElse(null);

        LocalDateTime arrivalTime = model.arrivalTime()
                .map(time -> LocalDateTime.ofInstant(time, zoneId))
                .orElse(null);

        return new GetTaxiRideResponse(
                model.status(),
                startTime,
                arrivalTime,
                driverInfo
        );
    }
}
