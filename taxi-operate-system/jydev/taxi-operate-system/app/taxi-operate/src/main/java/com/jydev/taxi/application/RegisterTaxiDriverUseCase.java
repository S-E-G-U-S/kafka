package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiDriver;
import com.jydev.taxi.domain.TaxiDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterTaxiDriverUseCase {

    private final TaxiDriverRepository taxiDriverRepository;

    public long register(
            String driverName,
            String driverPhoneNumber,
            String vehicleNumber,
            int initialLocation
    ) {

        TaxiDriver taxiDriver = new TaxiDriver(
                driverName, 
                driverPhoneNumber, 
                vehicleNumber, 
                initialLocation
        );
        
        return taxiDriverRepository.save(taxiDriver)
                .getId();
    }
}
