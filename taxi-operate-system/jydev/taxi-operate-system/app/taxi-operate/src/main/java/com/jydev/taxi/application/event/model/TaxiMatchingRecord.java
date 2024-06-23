package com.jydev.taxi.application.event.model;

import java.time.Instant;

public record TaxiMatchingRecord(
        long callId,
        long driverId,
        Instant assignTime,
        String driverName,
        String driverPhoneNumber,
        String vehicleNumber
) {
}
