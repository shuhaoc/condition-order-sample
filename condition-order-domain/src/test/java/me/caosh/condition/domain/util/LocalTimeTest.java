package me.caosh.condition.domain.util;

import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author caoshuhao@touker.com
 * @date 2018/1/27
 */
public class LocalTimeTest {

    private static final LocalTime END_OF_DAY = new LocalTime(
            23, 59, 59, 999);

    @Test
    public void test() throws Exception {
        LocalTime localTime = new LocalTime(10, 0, 0);
        assertTrue(localTime.isAfter(LocalTime.MIDNIGHT));
        assertTrue(localTime.isBefore(END_OF_DAY));
    }
}
