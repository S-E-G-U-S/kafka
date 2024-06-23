package com.jydev.taxi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaxiDriverTest {

    @Test
    void 유효한_택시_정보인_경우_예외가_발생되지_않아야합니다() {
        long driverId = 1L;
        String driverName = "김택시";
        String driverPhoneNumber = "010-1234-5678";
        String vehicleNumber = "23구4444";

        TaxiDriver taxiDriver = new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber);

        Assertions.assertEquals(taxiDriver.getDriverId(), driverId);
        Assertions.assertEquals(taxiDriver.getDriverName(), driverName);
        Assertions.assertEquals(taxiDriver.getDriverPhoneNumber(), driverPhoneNumber);
        Assertions.assertEquals(taxiDriver.getVehicleNumber(), vehicleNumber);
    }

    @Test
    void 운전자의_이름은_비어있을_수_없습니다() {
        long driverId = 1L;
        String driverName = "";
        String driverPhoneNumber = "010-1234-5678";
        String vehicleNumber = "23구4444";

        Assertions.assertThrows(IllegalArgumentException.class,() -> new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber));
    }

    @Test
    void 운전자의_전화번호는_비어있을_수_없습니다() {
        long driverId = 1L;
        String driverName = "김택시";
        String driverPhoneNumber = "";
        String vehicleNumber = "23구4444";

        Assertions.assertThrows(IllegalArgumentException.class,() -> new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber));
    }

    @Test
    void 운전자의_차량번호는_비어있을_수_없습니다() {
        long driverId = 1L;
        String driverName = "김택시";
        String driverPhoneNumber = "010-1234-5678";
        String vehicleNumber = "";

        Assertions.assertThrows(IllegalArgumentException.class,() -> new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber));
    }

    @Test
    void 운전자의_이름은_널이될_수_없습니다() {
        long driverId = 1L;
        String driverName = null;
        String driverPhoneNumber = "010-1234-5678";
        String vehicleNumber = "23구4444";

        Assertions.assertThrows(IllegalArgumentException.class,() -> new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber));
    }

    @Test
    void 운전자의_전화번호는_널이될_수_없습니다() {
        long driverId = 1L;
        String driverName = "김택시";
        String driverPhoneNumber = null;
        String vehicleNumber = "23구4444";

        Assertions.assertThrows(IllegalArgumentException.class,() -> new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber));
    }

    @Test
    void 운전자의_차량번호는_널이될_수_없습니다() {
        long driverId = 1L;
        String driverName = "김택시";
        String driverPhoneNumber = "010-1234-5678";
        String vehicleNumber = null;

        Assertions.assertThrows(IllegalArgumentException.class,() -> new TaxiDriver(driverId, driverName, driverPhoneNumber, vehicleNumber));
    }
}
