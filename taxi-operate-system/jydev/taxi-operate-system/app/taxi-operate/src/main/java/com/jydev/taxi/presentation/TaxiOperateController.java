package com.jydev.taxi.presentation;

import com.jydev.taxi.application.*;
import com.jydev.taxi.presentation.model.request.CompleteOperationCallRequest;
import com.jydev.taxi.presentation.model.request.RegisterTaxiDriverRequest;
import com.jydev.taxi.presentation.model.request.RequestAssignCallRequest;
import com.jydev.taxi.presentation.model.request.StartOperationCallRequest;
import com.jydev.taxi.presentation.model.response.ListWaitingCallResponse;
import com.jydev.taxi.presentation.model.response.RegisterTaxiDriverResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TaxiOperateController {

    private final RegisterTaxiDriverUseCase registerTaxiDriverUseCase;
    private final AssignTaxiCallRequestUseCase assignTaxiCallRequestUseCase;
    private final GetWaitingCallsUseCase getWaitingCallsUseCase;
    private final StartTaxiCallOperationUseCase startTaxiCallOperationUseCase;
    private final CompleteTaxiCallOperationUseCase completeTaxiCallOperationUseCase;

    @PostMapping("/taxi-driver")
    public RegisterTaxiDriverResponse registerTaxiDriver(@RequestBody @Valid RegisterTaxiDriverRequest registerRequest) {

        long driverId = registerTaxiDriverUseCase.register(
                registerRequest.driverName(),
                registerRequest.driverPhoneNumber(),
                registerRequest.vehicleNumber(),
                registerRequest.initialLocation()
        );

        return new RegisterTaxiDriverResponse(driverId);
    }

    @GetMapping("/waiting-call")
    public ListWaitingCallResponse listWaitingCall(
            @RequestParam long driverId,
            @RequestParam @Valid @Min(5) int locationRadius
    ) {

        var callIds = getWaitingCallsUseCase.getWaitingCalls(driverId, locationRadius);

        var items = callIds.stream()
                .map(ListWaitingCallResponse.ListWaitingCallItem::new)
                .toList();

        return new ListWaitingCallResponse(items);
    }

    @PostMapping("/call/{callId}/assign")
    public void requestAssignCall(
            @PathVariable long callId,
            @RequestBody RequestAssignCallRequest assignRequest
    ) {
        assignTaxiCallRequestUseCase.assignRequest(callId, assignRequest.driverId());
    }

    @PostMapping("/operation/{operationCallId}/start")
    public void startOperationCall(
            @PathVariable long operationCallId,
            @RequestBody StartOperationCallRequest request
    ) {
        startTaxiCallOperationUseCase.startOperationCall(operationCallId, request.driverId());
    }

    @PostMapping("/operation/{operationCallId}/complete")
    public void completeOperationCall(
            @PathVariable long operationCallId,
            @RequestBody CompleteOperationCallRequest request
    ) {
        completeTaxiCallOperationUseCase.completeOperationCall(operationCallId, request.driverId());
    }
}
