package hbec.intellitrade.strategy.domain.timerange;

import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author caoshuhao@touker.com
 * @date 2018/1/28
 */
public class LocalTimeRangeTest {
    @Test
    public void testNormal() throws Exception {
        LocalTimeRange timeRange = new LocalTimeRange(new LocalTime(10, 0, 0),
                new LocalTime(11, 0, 0));
        assertFalse(timeRange.isTimeInRange(new LocalTime(9, 59, 59)));
        assertTrue(timeRange.isTimeInRange(new LocalTime(10, 0, 0)));
        assertTrue(timeRange.isTimeInRange(new LocalTime(10, 0, 1)));
        assertTrue(timeRange.isTimeInRange(new LocalTime(11, 0, 0)));
        assertFalse(timeRange.isTimeInRange(new LocalTime(11, 0, 1)));

        assertEquals(timeRange.getBeginTime(), new LocalTime(10, 0, 0));
        assertEquals(timeRange.getEndTime(), new LocalTime(11, 0, 0));
        assertEquals(timeRange.toString(), "LocalTimeRange[10:00:00.000, 11:00:00.000]");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegal() throws Exception {
        new LocalTimeRange(new LocalTime(10, 0, 0),
                new LocalTime(9, 0, 0));
    }
}
