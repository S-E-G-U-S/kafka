package com.jydev.taxi.application.event.model;

import java.time.Instant;
import java.util.Objects;

public record TaxiRideStatusRecord(long callId, Status status, Instant timestamp) {

    public TaxiRideStatusRecord {
        Objects.requireNonNull(status);
        Objects.requireNonNull(timestamp);
    }

    public enum Status {
        START_RIDE,
        COMPLETE_RIDE
    }
}