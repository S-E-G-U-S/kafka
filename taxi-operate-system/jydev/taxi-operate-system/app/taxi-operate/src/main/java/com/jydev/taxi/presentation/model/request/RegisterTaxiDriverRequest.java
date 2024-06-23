package com.jydev.taxi.presentation.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterTaxiDriverRequest(
        @NotBlank
        String driverName,
        @NotBlank
        String driverPhoneNumber,
        @NotBlank
        String vehicleNumber,
        @Size(min = 1)
        int initialLocation
) {
}
