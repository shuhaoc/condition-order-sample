package me.caosh.condition.domain.util;


import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by caosh on 2017/9/3.
 */
public class DateFormats {
    public static final DateTimeFormatter HH_MM_SS = DateTimeFormat.forPattern("HH:mm:ss");

    private DateFormats() {
    }
}
