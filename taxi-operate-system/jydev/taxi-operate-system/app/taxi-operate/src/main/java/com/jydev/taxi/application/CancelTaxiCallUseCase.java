package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiCallAssignment;
import com.jydev.taxi.domain.TaxiCallAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CancelTaxiCallUseCase {

    private final TaxiCallAssignmentRepository assignmentRepository;

    @Transactional
    public void cancelAssignment(long callId) {

        TaxiCallAssignment assignment = assignmentRepository.findByCallId(callId).orElseThrow();
        assignment.cancelCall();
    }
}
