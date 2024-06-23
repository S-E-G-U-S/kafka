package com.jydev.taxi.application.event;

import com.jydev.taxi.application.event.model.TaxiRideStatusRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxiRideStatusPublisher {

    private static final String TOPIC = "taxi_ride_status_json_topic";
    private final KafkaTemplate<String, TaxiRideStatusRecord> kafkaTemplate;

    @TransactionalEventListener
    public void publish(TaxiRideStatusRecord record) {
        kafkaTemplate.send(TOPIC, record);
        log.info("publish Taxi Ride Status: {}", record);
    }
}
