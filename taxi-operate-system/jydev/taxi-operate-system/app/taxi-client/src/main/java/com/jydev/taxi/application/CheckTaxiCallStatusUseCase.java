package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiCall;
import com.jydev.taxi.domain.TaxiCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class CheckTaxiCallStatusUseCase {

    private static final int CALL_TIMEOUT_MINUTE = 1;
    private final TaxiCallRepository taxiCallRepository;

    public TaxiCall.Status checkTaxiCallStatus(long callId, Instant requestTime) {

        TaxiCall taxiCall = taxiCallRepository.findById(callId).orElseThrow();

        taxiCall.cancelIfTimeoutExceeded(requestTime, CALL_TIMEOUT_MINUTE);

        return taxiCall.getStatus();

    }
}
