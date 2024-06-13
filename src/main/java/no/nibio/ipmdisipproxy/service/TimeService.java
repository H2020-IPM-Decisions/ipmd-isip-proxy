package no.nibio.ipmdisipproxy.service;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * TimeService was introduced in order for the unit tests to be able to manipulate current date
 */
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