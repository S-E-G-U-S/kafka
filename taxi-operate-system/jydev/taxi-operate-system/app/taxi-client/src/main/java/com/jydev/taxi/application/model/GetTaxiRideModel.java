package com.jydev.taxi.application.model;

import com.jydev.taxi.domain.TaxiRide;

import java.time.Instant;
import java.util.Optional;

public record GetTaxiRideModel(
        TaxiRide.Status status,
        Optional<Instant> startTime,
        Optional<Instant> arrivalTime,
        long driverId,
        String driverName,
        String driverPhoneNumber,
        String vehicleNumber
) {

}
