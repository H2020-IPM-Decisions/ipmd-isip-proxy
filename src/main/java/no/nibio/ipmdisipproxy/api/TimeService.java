package no.nibio.ipmdisipproxy.api;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Service
public class TimeService {
    private final Clock clock;

    public TimeService(Clock clock) {
        this.clock = clock;
    }

    public ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now(clock);
    }

    public LocalDate getCurrentDate() {
        return getCurrentTime().toLocalDate();
    }
}