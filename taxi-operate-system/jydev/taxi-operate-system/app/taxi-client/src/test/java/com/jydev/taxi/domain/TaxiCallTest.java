package com.jydev.taxi.domain;

import com.jydev.taxi.domain.mock.TestTaxiCall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

public class TaxiCallTest {


    @Test
    void 택시_호출_생성시_첫_상태는_PENDING_상태여야합니다() {
        long userId = 1L;
        int departure = 10;
        int arrival = 20;

        TaxiCall taxiCall = new TaxiCall(userId, departure, arrival);

        Assertions.assertEquals(userId, taxiCall.getUserId());
        Assertions.assertEquals(departure, taxiCall.getDepartures());
        Assertions.assertEquals(arrival, taxiCall.getArrivals());
        Assertions.assertEquals(TaxiCall.Status.PENDING, taxiCall.getStatus());
    }

    @Test
    void 택시_호출_취소후_상태는_CANCELED로_변경_되어야합니다() {
        long userId = 1L;
        int departure = 10;
        int arrival = 20;

        TaxiCall taxiCall = new TaxiCall(userId, departure, arrival);
        taxiCall.cancel();

        Assertions.assertEquals(TaxiCall.Status.CANCELED, taxiCall.getStatus());
    }

    @Test
    void 기사_할당이된_후_상태는_CONFIRMED로_변경_되어야합니다() {
        int departure = 10;
        int arrival = 20;

        TaxiCall taxiCall = new TestTaxiCall(departure, arrival, Instant.now(), Instant.now());

        long driverId = 2L;
        int timeoutMinute = 10;

        taxiCall.assignDriver(driverId, Instant.now(), timeoutMinute);

        Assertions.assertEquals(TaxiCall.Status.CONFIRMED, taxiCall.getStatus());
    }

    @Test
    void 이미_취소된_택시_호출을_다시_취소하면_예외_발생() {
        long userId = 1L;
        int departure = 10;
        int arrival = 20;

        TaxiCall taxiCall = new TaxiCall(userId, departure, arrival);
        taxiCall.cancel();

        Assertions.assertThrows(IllegalStateException.class, taxiCall::cancel);
    }

    @Test
    void 이미_취소된_택시_호출에_기사를_할당하면_예외_발생() {
        int departure = 10;
        int arrival = 20;

        TaxiCall taxiCall = new TestTaxiCall(departure, arrival, Instant.now(), Instant.now());
        taxiCall.cancel();

        long driverId = 2L;
        int timeoutMinute = 10;


        Assertions.assertThrows(IllegalStateException.class, () -> taxiCall.assignDriver(driverId, Instant.now(), timeoutMinute));
    }

    @Test
    void 기사를_등록할_때_Call_요청이_타임아웃에의해_만료된_경우_예외가_발생하고_Call이_취소되야_합니다() {
        int departure = 10;
        int arrival = 20;

        Instant creationTime = Instant.now();

        TaxiCall taxiCall = new TestTaxiCall(departure, arrival, creationTime, creationTime);
        taxiCall.cancel();

        long driverId = 2L;

        Assertions.assertThrows(IllegalStateException.class, () -> taxiCall.assignDriver(driverId, creationTime.plusSeconds(20), 0));
        Assertions.assertEquals(taxiCall.getStatus(), TaxiCall.Status.CANCELED);
    }

    @Test
    void Call_요청이_타임아웃에의해_만료된_경우_Call이_취소되야_합니다() {
        int departure = 10;
        int arrival = 20;

        Instant creationTime = Instant.now();

        TaxiCall taxiCall = new TestTaxiCall(departure, arrival, creationTime, creationTime);
        int timeoutMinute = 30;
        Instant timeoutTime = creationTime.plus(Duration.ofMinutes(timeoutMinute + 1));

        taxiCall.cancelIfTimeoutExceeded(timeoutTime, timeoutMinute);

        Assertions.assertEquals(taxiCall.getStatus(), TaxiCall.Status.CANCELED);
    }

}
