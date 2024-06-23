package com.jydev.taxi.application.event;

import com.jydev.taxi.application.AssignTaxiCallMatchingUseCase;
import com.jydev.taxi.application.CancelTaxiCallUseCase;
import com.jydev.taxi.application.event.model.TaxiMatchingResultRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaxiMatchingResultSubscriber {

    private final AssignTaxiCallMatchingUseCase assignTaxiCallMatchingUseCase;
    private final CancelTaxiCallUseCase cancelTaxiCallUseCase;

    @KafkaListener(topics = "taxi_matching_result_json_topic", groupId = "taxi-operate-group")
    public void subscribe(TaxiMatchingResultRecord taxiMatchingResultRecord) {

        switch (taxiMatchingResultRecord.resultStatus()) {
            case ACCEPT -> assignTaxiCallMatchingUseCase.assignTaxiCall(
                    taxiMatchingResultRecord.callId(),
                    taxiMatchingResultRecord.driverId()
            );
            case CANCEL -> cancelTaxiCallUseCase.cancelAssignment(taxiMatchingResultRecord.callId());
        }

        log.info("Receive Taxi Matching Result : {}", taxiMatchingResultRecord);
    }
}
