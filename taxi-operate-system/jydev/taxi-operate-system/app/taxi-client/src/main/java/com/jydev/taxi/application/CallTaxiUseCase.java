package com.jydev.taxi.application;

import com.jydev.taxi.application.event.model.TaxiCallRecord;
import com.jydev.taxi.domain.TaxiCall;
import com.jydev.taxi.domain.TaxiCallRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CallTaxiUseCase {

    private final TaxiCallRepository taxiCallRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public long call(long userId, int departures, int arrivals) {

        TaxiCall taxiCall = new TaxiCall(userId, departures, arrivals);
        TaxiCall call = taxiCallRepository.save(taxiCall);
        TaxiCallRecord record = new TaxiCallRecord(taxiCall.getId(), taxiCall.getDepartures(), taxiCall.getArrivals());

        eventPublisher.publishEvent(record);

        return call.getId();
    }
}
