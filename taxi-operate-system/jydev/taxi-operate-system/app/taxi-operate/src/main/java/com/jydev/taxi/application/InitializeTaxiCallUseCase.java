package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiCallAssignment;
import com.jydev.taxi.domain.TaxiCallAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InitializeTaxiCallUseCase {

    private final TaxiCallAssignmentRepository assignmentRepository;

    public void initializeCallAssignment(long callId, int arrivals, int departures) {

        TaxiCallAssignment assignment = new TaxiCallAssignment(callId, departures, arrivals);
        assignmentRepository.save(assignment);
    }
}
