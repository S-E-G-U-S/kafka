package seosaju.taxi.domain.taxi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxiRepository extends JpaRepository<Taxi, Long> {
    List<Taxi> findAllByStatus(TaxiStatus status);
}
