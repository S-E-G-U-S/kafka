package com.jydev.taxi.application.event;

import com.jydev.taxi.application.CompleteRideUseCase;
import com.jydev.taxi.application.StartRideUseCase;
import com.jydev.taxi.application.event.model.TaxiRideStatusRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaxiRideStatusSubscriber {

    private final StartRideUseCase startRideUseCase;
    private final CompleteRideUseCase completeRideUseCase;


    @KafkaListener(topics = "taxi_ride_status_json_topic", groupId = "taxi-client-group")
    public void subscribe(TaxiRideStatusRecord rideStatusRecord) {

        switch (rideStatusRecord.status()) {
            case START_RIDE -> startRideUseCase.startRide(rideStatusRecord.callId(), rideStatusRecord.timestamp());
            case COMPLETE_RIDE ->
                    completeRideUseCase.completeRide(rideStatusRecord.callId(), rideStatusRecord.timestamp());
        }

        log.info("Taxi ride Status Change. Record : {}", rideStatusRecord);
    }
}
