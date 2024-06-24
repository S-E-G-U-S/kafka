package seosaju.taxi.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import seosaju.taxi.domain.passenger.Passenger;
import seosaju.taxi.domain.passenger.PassengerRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class PassengerProvider implements ApplicationRunner {

    private final PassengerRepository passengerRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        log.info("new passenger");
        Passenger passenger = new Passenger("테스트1");

        passengerRepository.save(passenger);
    }
}
