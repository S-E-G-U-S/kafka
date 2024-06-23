package com.jydev.taxi.presentation;

import com.jydev.taxi.application.*;
import com.jydev.taxi.presentation.model.mapper.TaxiConvertor;
import com.jydev.taxi.presentation.model.request.CallTaxiRequest;
import com.jydev.taxi.presentation.model.request.RegisterUserRequest;
import com.jydev.taxi.presentation.model.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TaxiClientController {

    private final RegisterUserUseCase registerUserUseCase;
    private final CallTaxiUseCase callTaxiUseCase;
    private final GetTaxiCallUseCase getTaxiCallUseCase;
    private final CancelTaxiCallUseCase cancelTaxiCallUseCase;
    private final CheckTaxiCallStatusUseCase checkTaxiCallStatusUseCase;
    private final GetTaxiRideUseCase getTaxiRideUseCase;

    @PostMapping("/user")
    public RegisterUserResponse registerUser(@Valid @RequestBody RegisterUserRequest request) {

        long userId = registerUserUseCase.registerUser(request.name());

        return new RegisterUserResponse(userId);
    }

    @PostMapping("/taxi-call")
    public CallTaxiResponse callTaxi(@Valid @RequestBody CallTaxiRequest request) {

        long callId = callTaxiUseCase.call(
                request.userId(),
                request.departures(),
                request.arrivals()
        );

        return new CallTaxiResponse(callId);
    }

    @PostMapping("/taxi-call/{callId}/cancel")
    public void cancelCall(@PathVariable long callId) {

        cancelTaxiCallUseCase.cancelCall(callId);
    }

    @GetMapping("/taxi-call/{callId}")
    public GetTaxiCallResponse getTaxiCall(@PathVariable long callId) {

        var model = getTaxiCallUseCase.getTaxiCall(callId);

        return TaxiConvertor.convert(model);
    }

    @PostMapping("/taxi-call/{callId}/status")
    public CheckCallStatusResponse checkCallStatus(@PathVariable long callId) {

        var callStatus = checkTaxiCallStatusUseCase.checkTaxiCallStatus(callId, Instant.now());

        return new CheckCallStatusResponse(callStatus);
    }

    @GetMapping("/taxi-ride/{rideId}")
    public GetTaxiRideResponse getTaxiRide(@PathVariable long rideId) {

        var model = getTaxiRideUseCase.getTaxiRide(rideId);
        ZoneId zoneId = ZoneId.of("Asia/Seoul");

        return TaxiConvertor.convert(model, zoneId);
    }
}
