package seosaju.taxi.domain.matching;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "MATCHING")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "PASSENGER_ID")
    private long passengerId;

    @Column(name = "TAXI")
    private Long taxiId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private MatchingStatus status;

    @CreationTimestamp
    private LocalDateTime creationTime;

    public Matching(long passengerId) {

        if (isEnded() || isFailed()) {
            throw new IllegalStateException("Matching is already ended");
        }

        this.passengerId = passengerId;
        this.status = MatchingStatus.WAITING;
    }

    public void match(long taxiId) {

        if (isEnded() || isFailed()) {
            throw new IllegalStateException("Matching is already ended");
        }

        this.taxiId = taxiId;
        this.status = MatchingStatus.MATCHED;
    }

    public void end() {

        if (isFailed()) {
            throw new IllegalStateException("Matching is already ended");
        }

        this.status = MatchingStatus.ENDED;
    }

    public void fail() {

        if (isEnded()) {
            throw new IllegalStateException("Matching is already ended");
        }

        this.status = MatchingStatus.FAILED;
    }

    private boolean isEnded() {
        return this.status == MatchingStatus.ENDED;
    }

    private boolean isFailed() {
        return this.status == MatchingStatus.FAILED;
    }
}
