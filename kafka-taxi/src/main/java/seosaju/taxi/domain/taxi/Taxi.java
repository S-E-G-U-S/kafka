package seosaju.taxi.domain.taxi;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TAXI")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "VEHICLE_NUMBER")
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private TaxiStatus status;

    public Taxi(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        this.status = TaxiStatus.EMPTY;
    }

    public void reserve() {
        this.status = TaxiStatus.RESERVED;
    }

    public void run() {
        this.status = TaxiStatus.RUNNING;
    }

    public void arrive() {
        this.status = TaxiStatus.EMPTY;
    }
}
