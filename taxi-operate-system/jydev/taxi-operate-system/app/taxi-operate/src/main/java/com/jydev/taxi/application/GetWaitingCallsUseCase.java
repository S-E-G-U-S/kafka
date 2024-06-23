package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiCallAssignmentRepository;
import com.jydev.taxi.domain.TaxiDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetWaitingCallsUseCase {

    private final TaxiDriverRepository taxiDriverRepository;
    private final TaxiCallAssignmentRepository callAssignmentRepository;

    public List<Long> getWaitingCalls(long driverId, int locationRadius) {

        var driver = taxiDriverRepository.findById(driverId).orElseThrow();
        int currentLocation = driver.getLocation();

        int minDistance = Math.max(currentLocation - locationRadius, 1);
        int maxDistance = currentLocation + locationRadius;
        if (maxDistance < currentLocation) {
            maxDistance = Integer.MAX_VALUE;
        }

        return callAssignmentRepository.findWaitingCallIdsByDepartureRange(minDistance, maxDistance);
    }
}
