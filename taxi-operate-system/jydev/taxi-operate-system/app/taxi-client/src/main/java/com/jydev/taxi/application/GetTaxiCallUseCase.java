package com.jydev.taxi.application;

import com.jydev.taxi.application.model.GetTaxiCallModel;
import com.jydev.taxi.application.model.mapper.TaxiCallConvertor;
import com.jydev.taxi.domain.TaxiCall;
import com.jydev.taxi.domain.TaxiCallRepository;
import com.jydev.taxi.domain.TaxiRide;
import com.jydev.taxi.domain.TaxiRideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTaxiCallUseCase {

    private final TaxiCallRepository taxiCallRepository;
    private final TaxiRideRepository taxiRideRepository;

    public GetTaxiCallModel getTaxiCall(long callId) {

        var taxiCall = taxiCallRepository.findById(callId)
                .orElseThrow();

        TaxiRide taxiRide = null;

        boolean taxiMatched = taxiCall.getStatus() == TaxiCall.Status.CONFIRMED;
        if (taxiMatched) {
            taxiRide = taxiRideRepository.findByCallId(callId).orElseThrow();
        }

        return TaxiCallConvertor.convert(taxiCall, taxiRide);
    }

}
