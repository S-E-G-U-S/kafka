package seosaju.taxi.domain.passenger;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PASSENGER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private PassengerStatus status;

    public Passenger(String name) {
        this.name = name;
        this.status = PassengerStatus.IDLE;
    }

    public void call() {
        this.status = PassengerStatus.CALLING;
    }

    public void board() {
        this.status = PassengerStatus.BOARDED;
    }

    public void arrive() {
        this.status = PassengerStatus.IDLE;
    }

    public void notMatch() {
        this.status = PassengerStatus.IDLE;
    }
}
