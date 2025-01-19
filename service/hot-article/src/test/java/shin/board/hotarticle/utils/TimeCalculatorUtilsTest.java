package shin.board.hotarticle.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeCalculatorUtilsTest {

    @Test
    void test() {
        LocalDateTime now = LocalDateTime.of(2025, 2, 21, 22, 01, 31);
        Duration duration = TimeCalculatorUtils.calculateDurationToMidnight(now);

        System.out.println("duration.getSeconds() / 60 = " + duration.getSeconds() / 60);
    }
}