package com.jydev.taxi.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiDriver {

    @Column(name = "DRIVER_ID")
    private long driverId;

    @Column(name = "DRIVER_NAME")
    private String driverName;

    @Column(name = "DRIVER_PHONE_NUMBER")
    private String driverPhoneNumber;

    @Column(name = "VEHICLE_NUMBER")
    private String vehicleNumber;

    public TaxiDriver(long driverId, @NotNull String driverName, @NotNull String driverPhoneNumber, @NotNull String vehicleNumber) {

        if (!StringUtils.hasText(driverName)) {
            throw new IllegalArgumentException("Driver name cannot be empty");
        }

        if (!StringUtils.hasText(driverPhoneNumber)) {
            throw new IllegalArgumentException("Driver phone number cannot be empty");
        }

        if (!StringUtils.hasText(vehicleNumber)) {
            throw new IllegalArgumentException("Vehicle number cannot be empty");
        }

        this.driverId = driverId;
        this.driverName = driverName;
        this.driverPhoneNumber = driverPhoneNumber;
        this.vehicleNumber = vehicleNumber;
    }
}