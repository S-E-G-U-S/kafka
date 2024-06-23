package com.jydev.taxi.application.event;

import com.jydev.taxi.application.MatchTaxiCallUseCase;
import com.jydev.taxi.application.event.model.TaxiMatchingRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaxiMatchingSubscriber {

    private final MatchTaxiCallUseCase matchTaxiCallUseCase;


    @KafkaListener(topics = "taxi_matching_json_topic", groupId = "taxi-client-group")
    public void subscribe(TaxiMatchingRecord matchingRecord) {

        matchTaxiCallUseCase.matchTaxi(
                matchingRecord.callId(),
                matchingRecord.driverId(),
                matchingRecord.assignTime(),
                matchingRecord.driverName(),
                matchingRecord.driverPhoneNumber(),
                matchingRecord.vehicleNumber()
        );

        log.info("Taxi matching execute. Record : {}", matchingRecord);
    }
}
