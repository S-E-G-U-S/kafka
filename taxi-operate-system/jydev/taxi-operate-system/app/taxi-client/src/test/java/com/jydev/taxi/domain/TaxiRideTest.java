package com.jydev.taxi.domain;

import com.jydev.taxi.domain.mock.TestTaxiCall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class TaxiRideTest {


    private final TaxiDriver taxiDriver = new TaxiDriver(
            1L,
            "김택시",
            "010-1234-5678",
            "23구4444"
    );

    @Test
    void 택시_호출이_확정된_경우_탑승_객체가_생성되어야_한다() {

        TaxiCall confirmedTaxiCall = new TestTaxiCall( 10, 20, Instant.now(), Instant.now());
        confirmedTaxiCall.assignDriver(taxiDriver.getDriverId(), Instant.now(), 1);

        TaxiRide taxiRide = new TaxiRide(confirmedTaxiCall, taxiDriver);

        Assertions.assertNotNull(taxiRide);
        Assertions.assertEquals(confirmedTaxiCall.getId(), taxiRide.getCallId());
        Assertions.assertEquals(confirmedTaxiCall.getUserId(), taxiRide.getUserId());
        Assertions.assertEquals(confirmedTaxiCall.getDepartures(), taxiRide.getDepartures());
        Assertions.assertEquals(confirmedTaxiCall.getArrivals(), taxiRide.getArrivals());
        Assertions.assertEquals(taxiDriver, taxiRide.getTaxiDriver());
        Assertions.assertEquals(TaxiRide.Status.NOT_STARTED, taxiRide.getStatus());
    }

    @Test
    void 택시_호출이_확정되지_않은_경우_예외가_발생해야_한다() {

        TaxiCall unconfirmedTaxiCall = new TaxiCall(1L, 10, 20);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TaxiRide(unconfirmedTaxiCall, taxiDriver);
        });

        Assertions.assertEquals("Taxi call status is confirmed. Call Id : null", exception.getMessage());
    }

    @Test
    void 승객이_탑승_시작하면_상태가_변경되고_시작_시간이_설정되어야_한다() {

        TaxiCall confirmedTaxiCall = new TestTaxiCall( 10, 20, Instant.now(), Instant.now());
        confirmedTaxiCall.assignDriver(taxiDriver.getDriverId(), Instant.now(), 1);

        TaxiRide taxiRide = new TaxiRide(confirmedTaxiCall, taxiDriver);

        Instant startTime = Instant.now();
        taxiRide.startRide(startTime);

        Assertions.assertEquals(TaxiRide.Status.IN_PROGRESS, taxiRide.getStatus());
        Assertions.assertEquals(startTime, taxiRide.getStartTime());
    }

    @Test
    void 탑승이_시작되지_않은_경우_탑승_시작시_예외가_발생해야_한다() {

        TaxiCall confirmedTaxiCall = new TestTaxiCall( 10, 20, Instant.now(), Instant.now());
        confirmedTaxiCall.assignDriver(taxiDriver.getDriverId(), Instant.now(), 1);

        TaxiRide taxiRide = new TaxiRide(confirmedTaxiCall, taxiDriver);

        taxiRide.startRide(Instant.now());

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taxiRide.startRide(Instant.now());
        });

        Assertions.assertEquals("Taxi driver is already done. Call Id : " + taxiRide.getCallId(), exception.getMessage());
    }

    @Test
    void 승객이_도착하면_상태가_변경되고_도착_시간이_설정되어야_한다() {

        TaxiCall confirmedTaxiCall = new TestTaxiCall( 10, 20, Instant.now(), Instant.now());
        confirmedTaxiCall.assignDriver(taxiDriver.getDriverId(), Instant.now(), 1);

        TaxiRide taxiRide = new TaxiRide(confirmedTaxiCall, taxiDriver);

        taxiRide.startRide(Instant.now());

        Instant arrivalTime = Instant.now();
        taxiRide.completeRide(arrivalTime);

        Assertions.assertEquals(TaxiRide.Status.COMPLETED, taxiRide.getStatus());
        Assertions.assertEquals(arrivalTime, taxiRide.getArrivalTime());
    }

    @Test
    void 탑승이_진행_중이지_않은_경우_도착_완료시_예외가_발생해야_한다() {

        TaxiCall confirmedTaxiCall = new TestTaxiCall( 10, 20, Instant.now(), Instant.now());
        confirmedTaxiCall.assignDriver(taxiDriver.getDriverId(), Instant.now(), 1);

        TaxiRide taxiRide = new TaxiRide(confirmedTaxiCall, taxiDriver);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> taxiRide.completeRide(Instant.now()));

        Assertions.assertEquals("Taxi driver is not in progress. Call Id : " + taxiRide.getCallId(), exception.getMessage());
    }

}
