package me.caosh.condition.domain.util;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Date;

/**
 * Created by caosh on 2017/8/20.
 */
public class InstantUtils {
    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime.toDate();
    }

    public static Date toDate(LocalDate localDate) {
        return localDate.toDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.fromDateFields(date);
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    private InstantUtils() {
    }
}
