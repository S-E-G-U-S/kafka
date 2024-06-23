package com.jydev.taxi.application;

import com.jydev.taxi.application.event.model.TaxiMatchingResultRecord;
import com.jydev.taxi.domain.TaxiCallRepository;
import com.jydev.taxi.domain.TaxiDriver;
import com.jydev.taxi.domain.TaxiRide;
import com.jydev.taxi.domain.TaxiRideRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchTaxiCallUseCase {

    private static final int TAXI_CALL_TIMEOUT_MINUTES = 1;
    private final TaxiCallRepository taxiCallRepository;
    private final TaxiRideRepository taxiRideRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void matchTaxi(
            long callId,
            long driverId,
            Instant assignTime,
            String driverName,
            String driverPhoneNumber,
            String vehicleNumber
    ) {
        var optionalTaxiCall = taxiCallRepository.findById(callId);

        optionalTaxiCall.ifPresentOrElse(taxiCall -> {

            try {
                taxiCall.assignDriver(driverId, assignTime, TAXI_CALL_TIMEOUT_MINUTES);

                var taxiDriver = new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber);
                var taxiRide = new TaxiRide(taxiCall, taxiDriver);
                taxiRideRepository.save(taxiRide);

                sendMatchingAccept(callId, driverId);
            } catch (Exception ex) {

                log.info("matchTaxi call failed. Call Id: {}, reason : {}", callId, ex.getMessage());
                taxiCall.cancel();

                sendMatchCancel(callId, driverId);
            }

        }, () -> sendMatchCancel(callId, driverId));
    }

    private void sendMatchingAccept(long callId, long driverId) {

        TaxiMatchingResultRecord matchingResult = new TaxiMatchingResultRecord(callId, driverId, TaxiMatchingResultRecord.ResultStatus.ACCEPT);

        eventPublisher.publishEvent(matchingResult);
    }

    private void sendMatchCancel(long callId, long driverId) {

        TaxiMatchingResultRecord matchingResult = new TaxiMatchingResultRecord(callId, driverId, TaxiMatchingResultRecord.ResultStatus.CANCEL);

        eventPublisher.publishEvent(matchingResult);
    }
}
