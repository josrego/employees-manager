package me.jrego.employees.manager.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateUtils {
    public Integer calculateDaysDifferenceFromNow(LocalDate date) {
        return Period.ofDays(
                (int) ChronoUnit.DAYS.between(LocalDate.now(), date)).getDays();
    }
}
