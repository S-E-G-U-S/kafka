package com.jydev.taxi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiCallRepository extends JpaRepository<TaxiCall, Long> {
}
