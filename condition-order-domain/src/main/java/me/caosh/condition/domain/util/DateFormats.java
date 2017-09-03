package me.caosh.condition.domain.util;

import java.time.format.DateTimeFormatter;

/**
 * Created by caosh on 2017/9/3.
 */
public class DateFormats {
    public static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

    private DateFormats() {
    }
}
