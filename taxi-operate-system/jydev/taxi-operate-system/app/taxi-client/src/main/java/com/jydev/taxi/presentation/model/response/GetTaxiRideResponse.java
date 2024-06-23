package com.jydev.taxi.presentation.model.response;

import com.jydev.taxi.domain.TaxiRide;

import java.time.LocalDateTime;

public record GetTaxiRideResponse(
        TaxiRide.Status status,
        LocalDateTime startTime,
        LocalDateTime arrivalTime,
        TaxiDriverInfo driverInfo
) {

    public record TaxiDriverInfo(
            long driverId,
            String driverName,
            String driverPhoneNumber,
            String vehicleNumber
    ) {

    }
}
