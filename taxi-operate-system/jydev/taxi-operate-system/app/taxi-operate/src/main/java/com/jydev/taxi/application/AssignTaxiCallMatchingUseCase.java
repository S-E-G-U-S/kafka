package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiCallAssignment;
import com.jydev.taxi.domain.TaxiCallAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AssignTaxiCallMatchingUseCase {

    private final TaxiCallAssignmentRepository assignmentRepository;

    @Transactional
    public void assignTaxiCall(long callId, long driverId) {

        TaxiCallAssignment assignment = assignmentRepository.findByCallId(callId).orElseThrow();

        assignment.assignDriver(driverId);
    }
}
