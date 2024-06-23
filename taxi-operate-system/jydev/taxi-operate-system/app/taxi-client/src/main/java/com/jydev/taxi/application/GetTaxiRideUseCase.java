package com.jydev.taxi.application;

import com.jydev.taxi.application.model.GetTaxiRideModel;
import com.jydev.taxi.domain.TaxiDriver;
import com.jydev.taxi.domain.TaxiRide;
import com.jydev.taxi.domain.TaxiRideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GetTaxiRideUseCase {

    private final TaxiRideRepository taxiRideRepository;

    public GetTaxiRideModel getTaxiRide(long rideId) {

        TaxiRide taxiRide = taxiRideRepository.findById(rideId).orElseThrow();
        TaxiDriver driver = taxiRide.getTaxiDriver();

        return new GetTaxiRideModel(
                taxiRide.getStatus(),
                Optional.ofNullable(taxiRide.getStartTime()),
                Optional.ofNullable(taxiRide.getArrivalTime()),
                driver.getDriverId(),
                driver.getDriverName(),
                driver.getDriverPhoneNumber(),
                driver.getVehicleNumber()
        );
    }
}
