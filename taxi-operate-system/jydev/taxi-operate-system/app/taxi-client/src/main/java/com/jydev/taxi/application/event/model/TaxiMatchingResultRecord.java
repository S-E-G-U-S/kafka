package com.jydev.taxi.application.event.model;

public record TaxiMatchingResultRecord(
        long callId,
        long driverId,
        ResultStatus resultStatus
) {
    public enum ResultStatus {
        ACCEPT, CANCEL
    }
}
