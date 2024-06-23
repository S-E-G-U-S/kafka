package com.jydev.taxi.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Slf4j
@Component
public class UtcCurrentDateTimeProvider implements DateTimeProvider {

    public static final String BEAN_NAME = "utcCurrentDateTimeProvider";

    @Override
    public Optional<TemporalAccessor> getNow() {
        ZonedDateTime utcNow = ZonedDateTime.now(Clock.systemUTC());
        log.info("Current UTC time: {}", utcNow);
        return Optional.of(utcNow);
    }

}
