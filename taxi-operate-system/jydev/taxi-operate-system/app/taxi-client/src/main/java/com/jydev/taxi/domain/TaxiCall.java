package com.jydev.taxi.domain;

import com.jydev.taxi.model.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Getter
@Entity
@Table(name = "TAXI_CALL")
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaxiCall extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "DEPARTURES")
    private int departures;

    @Column(name = "ARRIVALS")
    private int arrivals;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "DRIVER_ID")
    private Long driverId;

    public TaxiCall(long userId, int departures, int arrivals) {
        this.userId = userId;
        this.departures = departures;
        this.arrivals = arrivals;
        this.status = Status.PENDING;
    }

    public void cancel() {

        checkPendingStatus();

        status = Status.CANCELED;
    }

    public void cancelIfTimeoutExceeded(Instant target, int timeoutMinutes) {
        if (isTimeoutExceeded(target, timeoutMinutes)) {
            cancel();
        }
    }

    public void assignDriver(long driverId, Instant assignTime, int timeoutMinutes) {

        cancelIfTimeoutExceeded(assignTime, timeoutMinutes);
        checkPendingStatus();
        this.driverId = driverId;
        this.status = Status.CONFIRMED;
    }

    private boolean isTimeoutExceeded(Instant target, int timeoutMinutes) {
        Duration timeout = Duration.ofMinutes(timeoutMinutes);
        Duration elapsed = Duration.between(getCreationTimeUtc(), target);
        return elapsed.compareTo(timeout) > 0;
    }

    public void delete() {
        this.status = Status.DELETED;
    }

    private void checkPendingStatus() {

        if (this.status != Status.PENDING) {
            throw new IllegalStateException("Status is not PENDING");
        }
    }


    public enum Status {
        PENDING, CONFIRMED, CANCELED, DELETED
    }
}