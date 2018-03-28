package me.caosh.util;

import me.caosh.util.DateFormats;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/9/3.
 */
public class DateFormatsTest {
    @Test
    public void testLocalTimeFormat() throws Exception {
        LocalTime localTime = new LocalTime(9, 30, 0);
        String text = "09:30:00";
        assertEquals(text, DateFormats.HH_MM_SS.print(localTime));
        assertEquals(localTime, LocalTime.parse(text, DateFormats.HH_MM_SS));
    }
}
