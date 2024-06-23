package com.jydev.taxi.domain.mock;

import com.jydev.taxi.domain.TaxiCall;

import java.time.Instant;

public class TestTaxiCall extends TaxiCall {
    public TestTaxiCall(int departures, int arrivals ,Instant creationTime, Instant modificationTime) {
        super(0,departures, arrivals);
        this.creationTimeUtc = creationTime;
        this.lastModificationTimeUtc = modificationTime;
    }
}
