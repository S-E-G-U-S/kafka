package com.jydev.taxi.application;

import com.jydev.taxi.application.event.model.TaxiMatchingRecord;
import com.jydev.taxi.domain.TaxiCallOperation;
import com.jydev.taxi.domain.TaxiCallOperationRepository;
import com.jydev.taxi.domain.TaxiDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AssignTaxiCallRequestUseCase {

    private final ApplicationEventPublisher eventPublisher;
    private final TaxiDriverRepository taxiDriverRepository;
    private final TaxiCallOperationRepository callOperationRepository;

    @Transactional(readOnly = true)
    public void assignRequest(long callId, long driverId) {

        boolean driverAlreadyInProgress = callOperationRepository.existsByDriverIdAndStatus(
                driverId,
                TaxiCallOperation.Status.IN_PROGRESS
        );

        if(driverAlreadyInProgress) {
            throw new IllegalStateException("Driver already in progress");
        }

        var taxiDriver = taxiDriverRepository.findById(driverId).orElseThrow();
        var assignmentTime = Instant.now();

        var event = new TaxiMatchingRecord(
                callId,
                driverId,
                assignmentTime,
                taxiDriver.getDriverName(),
                taxiDriver.getDriverPhoneNumber(),
                taxiDriver.getVehicleNumber()
        );

        eventPublisher.publishEvent(event);

    }
}
