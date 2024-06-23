package com.jydev.taxi.presentation.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CallTaxiRequest(

        long userId,

        @Min(0)
        @Max(100)
        int departures,

        @Min(0)
        @Max(100)
        int arrivals
) {
}
