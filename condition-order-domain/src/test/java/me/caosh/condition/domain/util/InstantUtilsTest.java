package me.caosh.condition.domain.util;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/9/3.
 */
public class InstantUtilsTest {
    @Test
    public void testToLocalDate() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-09-03 00:00:00");
        LocalDate localDate = LocalDate.parse("2017-09-03");
        assertEquals(localDate, InstantUtils.toLocalDate(date));
        assertEquals(date, InstantUtils.toDate(localDate));
    }
}
