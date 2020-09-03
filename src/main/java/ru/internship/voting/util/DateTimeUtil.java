package ru.internship.voting.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateTimeUtil() {
    }

    public static LocalDate dateOrMin(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE;
    }

    public static LocalDate dateOrMax(LocalDate localDate) {
        return localDate != null ? localDate : MAX_DATE;
    }

    public static boolean isTimeToUpdate() {
        return LocalTime.now().isBefore(LocalTime.of(21, 0, 0));
    }
}