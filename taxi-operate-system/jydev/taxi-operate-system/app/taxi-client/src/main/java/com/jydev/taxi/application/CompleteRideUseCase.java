package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiRide;
import com.jydev.taxi.domain.TaxiRideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class CompleteRideUseCase {

    private final TaxiRideRepository taxiRideRepository;

    @Transactional
    public void completeRide(long callId, Instant completeTime) {

        TaxiRide taxiRide = taxiRideRepository.findByCallId(callId).orElseThrow();
        taxiRide.completeRide(completeTime);
    }
}
