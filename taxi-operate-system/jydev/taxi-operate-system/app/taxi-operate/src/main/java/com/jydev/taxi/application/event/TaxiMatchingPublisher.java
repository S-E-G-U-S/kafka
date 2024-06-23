package com.jydev.taxi.application.event;


import com.jydev.taxi.application.event.model.TaxiMatchingRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxiMatchingPublisher {

    private static final String TOPIC = "taxi_matching_json_topic";
    private final KafkaTemplate<String, TaxiMatchingRecord> kafkaTemplate;

    @TransactionalEventListener
    public void publish(TaxiMatchingRecord record) {
        kafkaTemplate.send(TOPIC, record);
        log.info("publish Taxi Matching : {}", record);
    }
}
