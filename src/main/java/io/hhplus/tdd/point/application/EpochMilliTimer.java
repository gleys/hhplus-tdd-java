package io.hhplus.tdd.point.application;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EpochMilliTimer implements Timer {

    @Override
    public long getCurrentTime() {
        return Instant.now().toEpochMilli();
    }
}
