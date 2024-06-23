package com.jydev.taxi.application.event;

import com.jydev.taxi.application.InitializeTaxiCallUseCase;
import com.jydev.taxi.application.event.model.TaxiCallRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaxiCallSubscriber {

    private final InitializeTaxiCallUseCase initializeTaxiCallUseCase;

    @KafkaListener(topics = "taxi_call_json_topic", groupId = "taxi-operate-group")
    public void subscribe(TaxiCallRecord taxiCallRecord) {

        initializeTaxiCallUseCase.initializeCallAssignment(
                taxiCallRecord.callId(),
                taxiCallRecord.arrivals(),
                taxiCallRecord.departures()
        );

        log.info("Receive Taxi Call : {}", taxiCallRecord);
    }
}
