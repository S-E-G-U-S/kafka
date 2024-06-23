package com.jydev.taxi.application;

import com.jydev.taxi.domain.TaxiClient;
import com.jydev.taxi.domain.TaxiClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterUserUseCase {

    private final TaxiClientRepository taxiClientRepository;

    public long registerUser(String name) {

        TaxiClient user = new TaxiClient(name);

        return taxiClientRepository.save(user)
                .getId();
    }
}
