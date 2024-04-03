package exercise.controller;

import exercise.daytime.Daytime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// BEGIN
@RestController
@RequiredArgsConstructor
public class WelcomeController {
    private final Daytime night;
    private final Daytime day;

    @GetMapping("/welcome")
    public String getDayPeriod() {
        if (calculateDateTime()) {
            return night.getName();
        }

        return day.getName();
    }

    private static boolean calculateDateTime() {
        return LocalDateTime.now().isAfter(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0, 0))
        )
                &&
                LocalDateTime.now().isBefore(
                        LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 0, 0))
                );
    }

}
// END
