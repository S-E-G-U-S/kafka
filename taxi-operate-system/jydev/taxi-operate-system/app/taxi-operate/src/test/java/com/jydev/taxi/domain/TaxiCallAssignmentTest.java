package com.jydev.taxi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaxiCallAssignmentTest {

    @Test
    void 운전자를_배정할_때_이미_운전자가_배정된_경우_예외가_발생해야합니다() {

        TaxiCallAssignment taxiCallAssignment = new TaxiCallAssignment(1,10,10);

        taxiCallAssignment.assignDriver(20);

        Assertions.assertThrows(IllegalStateException.class, () -> taxiCallAssignment.assignDriver(10));
    }
}
