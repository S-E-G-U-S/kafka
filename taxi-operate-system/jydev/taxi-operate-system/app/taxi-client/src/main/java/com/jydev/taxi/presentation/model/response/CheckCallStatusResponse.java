package com.jydev.taxi.presentation.model.response;

import com.jydev.taxi.domain.TaxiCall;

public record CheckCallStatusResponse(
        TaxiCall.Status callStatus
) {
}
