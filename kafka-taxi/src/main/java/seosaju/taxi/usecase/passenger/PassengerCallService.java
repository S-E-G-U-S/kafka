package seosaju.taxi.usecase.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seosaju.taxi.domain.matching.Matching;
import seosaju.taxi.domain.matching.MatchingRepository;
import seosaju.taxi.domain.matching.MatchingStatus;
import seosaju.taxi.domain.passenger.Passenger;
import seosaju.taxi.domain.passenger.PassengerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerCallService {

    private final PassengerRepository passengerRepository;
    private final MatchingRepository matchingRepository;
    private final KafkaProducer<String, String> kafkaProducer;

    @Transactional
    public void sendPassengerCall(long passengerId) {

        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();

        Matching matching = new Matching(passenger.getId());
        matchingRepository.save(matching);

        String message = String.valueOf(passenger.getId());
        log.info("send passenger call: {}", message);
        kafkaProducer.send(new ProducerRecord<>("passengerCall", message));
    }

    @Transactional
    @KafkaListener(topics = "taxiAccept", groupId = "taxi-group")
    public void listenTaxiAccepts(String message) {

        log.info("listenTaxiAccepts: {}", message);
        String[] parts = message.split(",");
        Long passengerId = Long.parseLong(parts[0]);
        Long taxiId = Long.parseLong(parts[1]);

        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();

        matchingRepository.findByPassengerIdAndTaxiIdAndStatus(passengerId, taxiId, MatchingStatus.MATCHED)
                .ifPresent(matching -> handleTaxiAccept(passenger, taxiId));
    }

    @Transactional
    @KafkaListener(topics = "taxiNotMatch", groupId = "taxi-group")
    public void listenTaxiNotMatch(String message) {

        log.info("listenTaxiNotMatch: {}", message);
        Long passengerId = Long.parseLong(message);

        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();

        matchingRepository.findByPassengerIdAndStatus(passengerId, MatchingStatus.WAITING)
                .ifPresent(matching -> handleTaxiNotMatch(passenger, matching));
    }

    @Transactional
    @KafkaListener(topics = "taxiArrive", groupId = "taxi-group")
    public void listenTaxiArrive(String message) {

        log.info("listenTaxiArrive: {}", message);
        String[] parts = message.split(",");
        Long passengerId = Long.parseLong(parts[0]);
        Long taxiId = Long.parseLong(parts[1]);

        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();

        matchingRepository.findByPassengerIdAndTaxiIdAndStatus(passengerId, taxiId, MatchingStatus.MATCHED)
                .ifPresent(matching -> handleTaxiArrive(passenger, matching, taxiId));
    }

    private void handleTaxiAccept(Passenger passenger, Long taxiId) {
        passenger.board();
        log.info("passenger: {}가 택시 {}를 탑승했습니다.", passenger.getId(), taxiId);
        String boardMessage = passenger.getId() + "," + taxiId;
        kafkaProducer.send(new ProducerRecord<>("boardTaxi", boardMessage));
    }

    private void handleTaxiArrive(Passenger passenger, Matching matching, Long taxiId) {
        passenger.arrive();
        log.info("passenger {}가 택시 {}에서 내렸습니다.", passenger.getId(), taxiId);
        matching.end();
    }

    private void handleTaxiNotMatch(Passenger passenger, Matching matching) {
        passenger.notMatch();
        matching.fail();
        log.info("passenger {}의 매칭이 취소되었습니다.", passenger.getId());
    }
}
