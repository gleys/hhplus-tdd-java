package io.hhplus.tdd.common.util;

import io.hhplus.tdd.point.application.Timer;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EpochMilliTimer implements Timer {

    @Override
    public long getCurrentTime() {
        return Instant.now().toEpochMilli();
    }
}
