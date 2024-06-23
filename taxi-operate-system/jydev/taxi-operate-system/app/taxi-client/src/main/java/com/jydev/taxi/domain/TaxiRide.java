package com.jydev.taxi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Getter
@Entity
@Table(name = "TAXI_RIDE")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiRide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CALL_ID")
    private Long callId;

    @Column(name = "DEPARTURES")
    private Integer departures;

    @Column(name = "ARRIVALS")
    private Integer arrivals;

    @Column(name = "START_TIME")
    private Instant startTime;

    @Column(name = "ARRIVAL_TIME")
    private Instant arrivalTime;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private TaxiDriver taxiDriver;

    public TaxiRide(TaxiCall taxiCall, TaxiDriver driver) {
        Objects.requireNonNull(taxiCall);
        Objects.requireNonNull(driver);

        boolean confirmedCall = taxiCall.getStatus() == TaxiCall.Status.CONFIRMED;
        if (!confirmedCall) {
            throw new IllegalArgumentException("Taxi call status is confirmed. Call Id : " + taxiCall.getId());
        }

        this.callId = taxiCall.getId();
        this.userId = taxiCall.getUserId();
        this.departures = taxiCall.getDepartures();
        this.arrivals = taxiCall.getArrivals();
        this.taxiDriver = driver;
        this.status = Status.NOT_STARTED;
    }

    public void startRide(Instant startTime) {

        boolean alreadyDone = this.status != Status.NOT_STARTED;
        if (alreadyDone) {
            throw new IllegalArgumentException("Taxi driver is already done. Call Id : " + this.callId);
        }

        this.startTime = startTime;
        this.status = Status.IN_PROGRESS;
    }

    public void completeRide(Instant arrivalTime) {

        boolean notInProgress = this.status != Status.IN_PROGRESS;
        if (notInProgress) {
            throw new IllegalArgumentException("Taxi driver is not in progress. Call Id : " + this.callId);
        }

        this.arrivalTime = arrivalTime;
        this.status = Status.COMPLETED;
    }

    public enum Status {
        NOT_STARTED, // 승객이 탑승하기 전
        IN_PROGRESS, // 탑승 중
        COMPLETED // 도착
    }
}
