package hbec.intellitrade.strategy.domain.timerange;

import hbec.intellitrade.strategy.domain.shared.Week;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author caoshuhao@touker.com
 * @date 2018/1/28
 */
public class WeekTimeRangeTest {
    @Test
    public void testNormal() throws Exception {
        WeekTimeRange weekTimeRange = new WeekTimeRange(
                new WeekRange(Week.TUE, Week.THU),
                new LocalTimeRange(
                        new LocalTime(14, 30, 0),
                        new LocalTime(15, 0, 0)));

        assertTrue(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 24,
                14, 45, 0)));
        assertFalse(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 24,
                14, 25, 0)));
        assertFalse(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 26,
                14, 45, 0)));

        assertEquals(weekTimeRange.getWeekRange(), new WeekRange(Week.TUE, Week.THU));
        assertEquals(weekTimeRange.getLocalTimeRange(), (new LocalTimeRange(
                new LocalTime(14, 30, 0),
                new LocalTime(15, 0, 0))));
        WeekTimeRange weekTimeRange2 = new WeekTimeRange(
                new WeekRange(Week.TUE, Week.THU),
                new LocalTimeRange(
                        new LocalTime(14, 30, 0),
                        new LocalTime(15, 0, 0)));
        assertEquals(weekTimeRange, weekTimeRange2);
        assertEquals(weekTimeRange.hashCode(), weekTimeRange2.hashCode());
        System.out.println(weekTimeRange);

        assertEquals(weekTimeRange.getOption(), MonitorTimeRangeOption.ENABLED);
    }

    @Test
    public void testNone() throws Exception {
        assertTrue(NoneMonitorTimeRange.NONE.isInRange(LocalDateTime.now()));
        assertEquals(NoneMonitorTimeRange.NONE.getOption(), MonitorTimeRangeOption.DISABLED);
    }
}
