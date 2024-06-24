package seosaju.taxi.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import seosaju.taxi.domain.taxi.Taxi;
import seosaju.taxi.domain.taxi.TaxiRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxiProvider implements ApplicationRunner {

    private final TaxiRepository taxiRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        log.info("new taxi");
        Taxi taxi = new Taxi("12가 3456");
        Taxi taxi1 = new Taxi("34나 4885");

        taxiRepository.save(taxi);
        taxiRepository.save(taxi1);
    }
}
