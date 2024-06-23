package com.jydev.taxi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class TaxiCallOperationTest {


    private TaxiCallAssignment taxiCallAssignment;

    @BeforeEach
    public void setUp() {
        taxiCallAssignment = new TaxiCallAssignment(1L, 1, 1);
        taxiCallAssignment.assignDriver(1L);
    }

    @Test
    void 택시_호출_할당이_정상적으로_초기화된다() {
        TaxiCallOperation taxiCallOperation = new TaxiCallOperation(taxiCallAssignment);

        Assertions.assertNotNull(taxiCallOperation);
        Assertions.assertEquals(taxiCallAssignment.getId(), taxiCallOperation.getAssignmentCallId());
        Assertions.assertEquals(taxiCallAssignment.getDriverId(), taxiCallOperation.getDriverId());
        Assertions.assertEquals(TaxiCallOperation.Status.NOT_STARTED, taxiCallOperation.getStatus());
    }

    @Test
    void 운행이_시작되면_상태가_IN_PROGRESS로_변경된다() {

        TaxiCallOperation taxiCallOperation = new TaxiCallOperation(taxiCallAssignment);
        Instant startTime = Instant.now();

        taxiCallOperation.startRide(startTime);

        Assertions.assertEquals(TaxiCallOperation.Status.IN_PROGRESS, taxiCallOperation.getStatus());
        Assertions.assertEquals(startTime, taxiCallOperation.getStartTime());
    }

    @Test
    void 운행이_시작된_후_다시_시작하려고_하면_예외가_발생한다() {

        TaxiCallOperation taxiCallOperation = new TaxiCallOperation(taxiCallAssignment);

        taxiCallOperation.startRide(Instant.now());

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taxiCallOperation.startRide(Instant.now());
        });

        Assertions.assertEquals("Taxi driver is already done. Assignment Call Id : " + taxiCallOperation.getAssignmentCallId(), exception.getMessage());
    }

    @Test
    void 운행_중인_상태에서_운행을_완료하면_상태가_COMPLETED로_변경된다() {

        TaxiCallOperation taxiCallOperation = new TaxiCallOperation(taxiCallAssignment);

        taxiCallOperation.startRide(Instant.now());
        Instant arrivalTime = Instant.now();
        taxiCallOperation.completeRide(arrivalTime);

        Assertions.assertEquals(TaxiCallOperation.Status.COMPLETED, taxiCallOperation.getStatus());
        Assertions.assertEquals(arrivalTime, taxiCallOperation.getArrivalTime());
    }

    @Test
    void 운행_중인_상태가_아닐_때_운행을_완료하려고_하면_예외가_발생한다() {

        TaxiCallOperation taxiCallOperation = new TaxiCallOperation(taxiCallAssignment);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taxiCallOperation.completeRide(Instant.now());
        });
    }
}
