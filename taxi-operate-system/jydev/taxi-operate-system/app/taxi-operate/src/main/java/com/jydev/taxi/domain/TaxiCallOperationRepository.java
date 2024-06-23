package com.jydev.taxi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxiCallOperationRepository extends JpaRepository<TaxiCallOperation, Long> {

    Optional<TaxiCallOperation> findByIdAndDriverId(long id, long driverId);

    boolean existsByDriverIdAndStatus(long driverId, TaxiCallOperation.Status status);
}
