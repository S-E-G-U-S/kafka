package com.jydev.taxi.application;

import com.jydev.taxi.application.event.model.TaxiRideStatusRecord;
import com.jydev.taxi.domain.TaxiCallAssignmentRepository;
import com.jydev.taxi.domain.TaxiCallOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class StartTaxiCallOperationUseCase {

    private final TaxiCallOperationRepository operationRepository;
    private final TaxiCallAssignmentRepository assignmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void startOperationCall(long operationCallId, long driverId) {

        var operationCall = operationRepository.findByIdAndDriverId(operationCallId, driverId)
                .orElseThrow();

        var startTime = Instant.now();

        operationCall.startRide(startTime);

        var assignmentCall = assignmentRepository.findById(operationCall.getAssignmentCallId())
                .orElseThrow();

        var event = new TaxiRideStatusRecord(
                assignmentCall.getCallId(),
                TaxiRideStatusRecord.Status.START_RIDE,
                startTime
        );

        eventPublisher.publishEvent(event);
    }
}
