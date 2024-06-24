package seosaju.taxi.domain.matching;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    Optional<Matching> findByPassengerIdAndStatus(Long passengerId, MatchingStatus status);

    Optional<Matching> findByPassengerIdAndTaxiIdAndStatus(Long passengerId, Long taxiId, MatchingStatus status);
}
