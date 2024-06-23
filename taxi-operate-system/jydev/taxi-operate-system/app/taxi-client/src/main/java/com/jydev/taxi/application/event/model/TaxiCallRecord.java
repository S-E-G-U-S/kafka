package com.jydev.taxi.application.event.model;

public record TaxiCallRecord(
        long callId,
        int departures,
        int arrivals
) {
}
