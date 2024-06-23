package com.jydev.taxi.application.event;

import com.jydev.taxi.application.event.model.TaxiCallRecord;
import com.jydev.taxi.domain.TaxiCall;
import com.jydev.taxi.domain.TaxiCallRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaxiCallPublisher {
    private static final String TOPIC = "taxi_call_json_topic";

    private final KafkaTemplate<String, TaxiCallRecord> kafkaTemplate;
    private final TaxiCallRepository taxiCallRepository;

    @TransactionalEventListener
    public void publish(TaxiCallRecord record) {
        CompletableFuture<SendResult<String, TaxiCallRecord>> future = kafkaTemplate.send(TOPIC, record);

        future.whenComplete((result, ex) -> {

            boolean notPublished = ex != null;
            if (notPublished) {
                handlePublishingFailure(record.callId());
                log.error("Taxi call publish failed", ex);
            } else {
                log.info("Taxi call published. Call id : {}", record.callId());
            }
        });
    }

    public void handlePublishingFailure(long callId) {
        try {
            rollbackTaxiCall(callId);
        } catch (Exception ex) {
            log.error("Error during rollback of Taxi call for callId: {}", callId, ex);
        }
    }

    public void rollbackTaxiCall(long callId) {
        TaxiCall taxiCall = taxiCallRepository.findById(callId).orElseThrow();
        taxiCall.delete();
        taxiCallRepository.save(taxiCall);
    }
}
