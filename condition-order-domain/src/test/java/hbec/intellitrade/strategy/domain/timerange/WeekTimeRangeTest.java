package hbec.intellitrade.strategy.domain.timerange;

import com.google.common.base.Optional;
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

        assertEquals(weekTimeRange.getWeekRange(), Optional.of(new WeekRange(Week.TUE, Week.THU)));
        assertEquals(weekTimeRange.getLocalTimeRange(), Optional.of(new LocalTimeRange(
                new LocalTime(14, 30, 0),
                new LocalTime(15, 0, 0))));
        assertEquals(weekTimeRange, new WeekTimeRange(
                new WeekRange(Week.TUE, Week.THU),
                new LocalTimeRange(
                        new LocalTime(14, 30, 0),
                        new LocalTime(15, 0, 0))));
        System.out.println(weekTimeRange.hashCode());
        System.out.println(weekTimeRange);
    }

    @Test
    public void testWeekOnly() throws Exception {
        WeekTimeRange weekTimeRange = new WeekTimeRange(
                new WeekRange(Week.TUE, Week.THU), null);

        assertTrue(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 24,
                14, 45, 0)));
        assertTrue(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 24,
                13, 45, 0)));
        assertFalse(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 26,
                14, 45, 0)));
    }

    @Test
    public void testTimeOnly() throws Exception {
        WeekTimeRange weekTimeRange = new WeekTimeRange(
                null,
                new LocalTimeRange(
                        new LocalTime(14, 30, 0),
                        new LocalTime(15, 0, 0)));

        assertTrue(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 24,
                14, 45, 0)));
        assertTrue(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 26,
                14, 45, 0)));
        assertFalse(weekTimeRange.isInRange(new LocalDateTime(2018, 1, 24,
                13, 45, 0)));
    }

    @Test
    public void testNone() throws Exception {
        assertTrue(NoneMonitorTimeRange.INSTANCE.isInRange(LocalDateTime.now()));
    }
}
