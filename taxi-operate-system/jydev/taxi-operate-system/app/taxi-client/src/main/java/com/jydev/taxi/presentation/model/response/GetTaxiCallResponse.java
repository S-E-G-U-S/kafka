package com.jydev.taxi.presentation.model.response;

import com.jydev.taxi.domain.TaxiCall;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record GetTaxiCallResponse(

        @NotNull
        TaxiCall.Status callStatus,

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

    }
}
