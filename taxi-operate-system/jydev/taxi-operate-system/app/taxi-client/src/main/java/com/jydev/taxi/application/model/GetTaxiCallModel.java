package com.jydev.taxi.application.model;

import com.jydev.taxi.domain.TaxiCall;
import jakarta.annotation.Nullable;
import org.springframework.util.StringUtils;

public record GetTaxiCallModel(
        TaxiCall.Status status,
        @Nullable
        TaxiRideInfo rideInfo
) {

    public record TaxiRideInfo(
            long rideId,
            long driverId,
            String driverName,
            String driverPhoneNumber,
            String vehicleNumber
    ) {
        public TaxiRideInfo {

            if (StringUtils.hasText(driverName)) {
                throw new IllegalArgumentException("driverName must not be empty");
            }

            if (StringUtils.hasText(driverPhoneNumber)) {
                throw new IllegalArgumentException("driverPhoneNumber must not be empty");
            }

            if (StringUtils.hasText(vehicleNumber)) {
                throw new IllegalArgumentException("vehicleNumber must not be empty");
            }
        }
    }
}
