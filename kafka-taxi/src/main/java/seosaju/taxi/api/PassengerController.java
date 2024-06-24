package seosaju.taxi.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seosaju.taxi.api.model.PassengerRequest;
import seosaju.taxi.usecase.passenger.PassengerCallService;

@RestController
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerCallService passengerCallService;

    @PostMapping("call")
    public ResponseEntity<Void> callTaxi(@RequestBody PassengerRequest request) {

        passengerCallService.sendPassengerCall(request.passengerId());
        return ResponseEntity.noContent().build();
    }
}
