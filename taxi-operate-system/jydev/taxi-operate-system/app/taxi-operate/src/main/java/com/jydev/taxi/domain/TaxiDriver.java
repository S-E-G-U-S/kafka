package com.jydev.taxi.domain;


import com.jydev.taxi.model.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Entity
@Table(name = "TAXI_DRIVER")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiDriver extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DRIVER_NAME")
    private String driverName;

    @Column(name = "DRIVER_PHONE_NUMBER")
    private String driverPhoneNumber;

    @Column(name = "VEHICLE_NUMBER")
    private String vehicleNumber;

    @Column(name = "LOCATION")
    private Integer location;

    public TaxiDriver(
            String driverName,
            String driverPhoneNumber,
            String vehicleNumber,
            int initialLocation
    ) {

        if (!StringUtils.hasText(driverName)) {
            throw new IllegalArgumentException("Driver name cannot be empty");
        }

        if (!StringUtils.hasText(driverPhoneNumber)) {
            throw new IllegalArgumentException("Driver phone number cannot be empty");
        }

        if (!StringUtils.hasText(vehicleNumber)) {
            throw new IllegalArgumentException("Vehicle number cannot be empty");
        }

        this.driverName = driverName;
        this.driverPhoneNumber = driverPhoneNumber;
        this.vehicleNumber = vehicleNumber;
        this.location = initialLocation;
    }

    public void updateDriverInfo(
            String driverName,
            String driverPhoneNumber,
            String vehicleNumber
    ) {
        if (!StringUtils.hasText(driverName)) {
            throw new IllegalArgumentException("Driver name cannot be empty");
        }

        if (!StringUtils.hasText(driverPhoneNumber)) {
            throw new IllegalArgumentException("Driver phone number cannot be empty");
        }

        if (!StringUtils.hasText(vehicleNumber)) {
            throw new IllegalArgumentException("Vehicle number cannot be empty");
        }

        this.driverName = driverName;
        this.driverPhoneNumber = driverPhoneNumber;
        this.vehicleNumber = vehicleNumber;
    }

    public void updateLocation(int location) {
        this.location = location;
    }
}
