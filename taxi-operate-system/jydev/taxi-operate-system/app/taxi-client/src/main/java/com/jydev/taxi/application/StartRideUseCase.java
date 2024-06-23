package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiRide;
import com.jydev.taxi.domain.TaxiRideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class StartRideUseCase {

    private final TaxiRideRepository taxiRideRepository;

    @Transactional
    public void startRide(long callId, Instant startTime) {

        TaxiRide taxiRide = taxiRideRepository.findByCallId(callId).orElseThrow();
        taxiRide.startRide(startTime);
    }
}
