package com.jydev.taxi.domain;


import com.jydev.taxi.model.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Getter
@Entity
@Table(name = "TAXI_CALL_OPERATION")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiCallOperation extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAXI_CALL_ASSIGNMENT_ID")
    private Long assignmentCallId;

    @Column(name = "DRIVER_ID")
    private Long driverId;

    @Column(name = "START_TIME")
    private Instant startTime;

    @Column(name = "ARRIVAL_TIME")
    private Instant arrivalTime;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;


    public TaxiCallOperation(TaxiCallAssignment taxiCallAssignment) {

        Objects.requireNonNull(taxiCallAssignment);
        boolean waitingCall = taxiCallAssignment.getDriverId() == null;
        if(waitingCall) {
            throw new IllegalStateException("Taxi Call Assignment was waiting for driver. Assignment ID: " + taxiCallAssignment.getId());
        }

        this.assignmentCallId = taxiCallAssignment.getId();
        this.driverId = taxiCallAssignment.getDriverId();
        this.status = Status.NOT_STARTED;
    }

    public void startRide(Instant startTime) {

        boolean alreadyDone = this.status != Status.NOT_STARTED;
        if (alreadyDone) {
            throw new IllegalArgumentException("Taxi driver is already done. Assignment Call Id : " + this.assignmentCallId);
        }

        this.startTime = startTime;
        this.status = Status.IN_PROGRESS;
    }

    public void completeRide(Instant arrivalTime) {

        boolean notInProgress = this.status != Status.IN_PROGRESS;
        if (notInProgress) {
            throw new IllegalArgumentException("Taxi driver is not in progress. Assignment Call Id : " + this.assignmentCallId);
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
