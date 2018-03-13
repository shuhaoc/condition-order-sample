package hbec.intellitrade.strategy.domain.timerange;

import hbec.intellitrade.strategy.domain.shared.Week;
import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author caoshuhao@touker.com
 * @date 2018/1/28
 */
public class WeekRangeTest {
    @Test
    public void testNormal() throws Exception {
        WeekRange weekRange = new WeekRange(Week.TUE, Week.THU);
        assertFalse(weekRange.isDateInRange(new LocalDate(2018, 1, 22)));
        assertTrue(weekRange.isDateInRange(new LocalDate(2018, 1, 23)));
        assertTrue(weekRange.isDateInRange(new LocalDate(2018, 1, 24)));
        assertTrue(weekRange.isDateInRange(new LocalDate(2018, 1, 25)));
        assertFalse(weekRange.isDateInRange(new LocalDate(2018, 1, 26)));

        assertEquals(weekRange.getBeginWeek(), Week.TUE);
        assertEquals(weekRange.getEndWeek(), Week.THU);
        System.out.println(weekRange.hashCode());
        assertEquals(weekRange.toString(), "LocalTimeRange[TUE, THU]");
    }

    @Test
    public void testDefaultValue() throws Exception {
        WeekRange defaultWeekRange = new WeekRange(null, null);
        WeekRange weekRange = new WeekRange(Week.MON, Week.FRI);
        assertEquals(defaultWeekRange, weekRange);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegal() throws Exception {
        new WeekRange(Week.FRI, Week.MON);
    }
}
