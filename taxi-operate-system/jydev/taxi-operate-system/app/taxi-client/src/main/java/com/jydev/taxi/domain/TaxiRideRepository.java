package com.jydev.taxi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxiRideRepository extends JpaRepository<TaxiRide, Long> {
    Optional<TaxiRide> findByCallId(long callId);
}
