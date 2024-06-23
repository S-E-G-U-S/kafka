package com.jydev.taxi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaxiClientTest {

    @Test
    void 택시_클라이언트_생성_테스트() {
        String name = "김사용자";

        TaxiClient taxiClient = new TaxiClient(name);

        Assertions.assertEquals(name, taxiClient.getName());
    }

    @Test
    void 이름이_빈값이면_예외_발생() {
        String name = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> new TaxiClient(name));
    }

    @Test
    void 이름이_null이면_예외_발생() {
        String name = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> new TaxiClient(name));
    }

    @Test
    void 이름이_공백만_있으면_예외_발생() {
        String name = "   ";

        Assertions.assertThrows(IllegalArgumentException.class, () -> new TaxiClient(name));
    }
}
