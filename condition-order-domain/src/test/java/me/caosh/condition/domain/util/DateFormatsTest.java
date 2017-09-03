package me.caosh.condition.domain.util;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by caosh on 2017/9/3.
 */
public class DateFormatsTest {
    @Test
    public void testLocalTimeFormat() throws Exception {
        LocalTime localTime = LocalTime.of(9, 30, 0);
        String text = "09:30:00";
        assertEquals(text, DateFormats.HH_MM_SS.format(localTime));
        assertEquals(localTime, LocalTime.parse(text, DateFormats.HH_MM_SS));
    }
}
