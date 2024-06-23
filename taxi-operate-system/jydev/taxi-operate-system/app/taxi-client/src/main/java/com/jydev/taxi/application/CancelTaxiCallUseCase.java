package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiCall;
import com.jydev.taxi.domain.TaxiCallRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CancelTaxiCallUseCase {

    private final TaxiCallRepository taxiCallRepository;

    @Transactional
    public void cancelCall(long callId) {

        TaxiCall taxiCall = taxiCallRepository.findById(callId)
                .orElseThrow();

        taxiCall.cancel();
    }

}
