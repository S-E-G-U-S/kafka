package com.jydev.taxi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiClientRepository extends JpaRepository<TaxiClient, Long> {
}
