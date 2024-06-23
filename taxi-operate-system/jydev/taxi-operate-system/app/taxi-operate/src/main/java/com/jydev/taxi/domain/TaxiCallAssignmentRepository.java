package com.jydev.taxi.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaxiCallAssignmentRepository extends JpaRepository<TaxiCallAssignment, Long> {

    Optional<TaxiCallAssignment> findByCallId(Long callId);

    @Query("SELECT t.callId FROM TaxiCallAssignment t WHERE t.departures BETWEEN :minLocation AND :maxLocation AND t.driverId IS NULL ORDER BY t.creationTimeUtc")
    List<Long> findWaitingCallIdsByDepartureRange(@Param("minLocation") int minLocation, @Param("maxLocation") int maxLocation);
}
