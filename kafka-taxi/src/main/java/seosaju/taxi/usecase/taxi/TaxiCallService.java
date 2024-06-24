package seosaju.taxi.usecase.taxi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seosaju.taxi.domain.matching.MatchingRepository;
import seosaju.taxi.domain.matching.MatchingStatus;
import seosaju.taxi.domain.taxi.Taxi;
import seosaju.taxi.domain.taxi.TaxiRepository;
import seosaju.taxi.domain.taxi.TaxiStatus;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxiCallService {

    private final TaxiRepository taxiRepository;
    private final MatchingRepository matchingRepository;
    private final KafkaProducer<String, String> kafkaProducer;

    @Transactional
    @KafkaListener(topics = "passengerCall", groupId = "taxi-group")
    public void listenPassengerCalls(String message) {

        log.info("passengerCall {}", message);
        Long passengerId = Long.parseLong(message);

        List<Taxi> availableTaxis = taxiRepository.findAllByStatus(TaxiStatus.EMPTY);

        Optional<Taxi> matchedTaxi = availableTaxis.stream()
                .filter(taxi -> createMatch(passengerId, taxi.getId()))
                .findFirst();

        matchedTaxi.ifPresentOrElse(
                taxi -> handleTaxiCall(taxi, passengerId),
                () -> handleTaxiNotMatch(passengerId));
    }

    @Transactional
    @KafkaListener(topics = "boardTaxi", groupId = "taxi-group")
    public void listenBoardTaxi(String message) {

        log.info("boardTaxi {}", message);
        String[] parts = message.split(",");
        Long passengerId = Long.parseLong(parts[0]);
        Long taxiId = Long.parseLong(parts[1]);

        taxiRepository.findById(taxiId).ifPresent(taxi -> {
            handleBoardTaxi(taxi, passengerId);
        });
    }

    private boolean createMatch(Long passengerId, Long taxiId) {

        return matchingRepository.findByPassengerIdAndStatus(passengerId, MatchingStatus.WAITING)
                .map(matching -> {
                    if (Math.random() < 0.5) { // 50%의 확률로 매칭을 거부
                        return false;
                    }
                    matching.match(taxiId);
                    matchingRepository.save(matching);
                    return true;
                })
                .orElse(false);
    }

    private void handleTaxiCall(Taxi taxi, Long passengerId) {

        taxi.reserve();
        taxiRepository.save(taxi);
        log.info("택시 {}가 승객 {}의 요청을 수락했습니다.", taxi.getId(), passengerId);


        String message = passengerId + "," + taxi.getId();
        kafkaProducer.send(new ProducerRecord<>("taxiAccept", message));
    }

    private void handleBoardTaxi(Taxi taxi, Long passengerId) {

        taxi.run();
        log.info("빛의 속도로 택시 {}가 승객 {}을 도착지에 데려다줍니다.", taxi.getId(), passengerId);
        taxi.arrive();

        String message = passengerId + "," + taxi.getId();
        kafkaProducer.send(new ProducerRecord<>("taxiArrive", message));
    }

    private void handleTaxiNotMatch(Long passengerId) {

        log.info("불쌍한 승객 {}은 택시를 잡지 못했습니다.", passengerId);

        String message = String.valueOf(passengerId);
        kafkaProducer.send(new ProducerRecord<>("taxiNotMatch", message));
    }
}
