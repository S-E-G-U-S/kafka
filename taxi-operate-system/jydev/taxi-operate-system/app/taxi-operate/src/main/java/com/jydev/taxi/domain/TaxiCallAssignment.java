package com.jydev.taxi.domain;

import com.jydev.taxi.model.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TAXI_CALL_ASSIGNMENT")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiCallAssignment extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAXI_CALL_ID")
    private Long callId;

    @Column(name = "DRIVER_ID")
    private Long driverId;

    @Column(name = "DEPARTURES")
    private Integer departures;

    @Column(name = "ARRIVALS")
    private Integer arrivals;

    @Column(name = "CANCELED")
    private Boolean canceled;

    public TaxiCallAssignment(long callId, int departures, int arrivals) {
        this.callId = callId;
        this.departures = departures;
        this.arrivals = arrivals;
    }

    public void assignDriver(long driverId) {

        if (this.driverId != null) {
            throw new IllegalStateException("this call already assigned driver");
        }

        this.driverId = driverId;
    }

    public void cancelCall() {
        this.canceled = true;
    }
}
