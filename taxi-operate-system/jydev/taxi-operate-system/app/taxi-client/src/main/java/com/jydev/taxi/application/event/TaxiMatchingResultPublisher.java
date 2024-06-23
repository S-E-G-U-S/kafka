package com.jydev.taxi.application.event;

import com.jydev.taxi.application.event.model.TaxiMatchingResultRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxiMatchingResultPublisher {

    private static final String TOPIC = "taxi_matching_result_json_topic";
    private final KafkaTemplate<String, TaxiMatchingResultRecord> kafkaTemplate;

    @TransactionalEventListener
    public void publish(TaxiMatchingResultRecord record) {
        kafkaTemplate.send(TOPIC, record);
        log.info("publish taxi Matching Result: {}", record);
    }
}
