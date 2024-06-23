package com.jydev.taxi;

import com.jydev.taxi.common.UtcCurrentDateTimeProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = UtcCurrentDateTimeProvider.BEAN_NAME)
public class TaxiClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiClientApplication.class, args);
    }

}
