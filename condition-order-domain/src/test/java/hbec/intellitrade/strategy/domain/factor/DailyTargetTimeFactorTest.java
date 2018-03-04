package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.Optional;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class DailyTargetTimeFactorTest {
    @Test
    public void testBasic() throws Exception {
        DailyTargetTimeFactor factor = new DailyTargetTimeFactor(new LocalTime(10, 0, 0));
        DailyTargetTimeFactor factor1 = new DailyTargetTimeFactor(new LocalTime(10, 0, 0));
        assertEquals(factor.getTargetTime(), new LocalTime(10, 0, 0));
        assertEquals(factor, factor1);
        assertEquals(factor.hashCode(), factor1.hashCode());
        System.out.println(factor);
    }

    @Test
    public void testCondition() throws Exception {
        DailyTargetTimeFactor factor = new DailyTargetTimeFactor(new LocalTime(10, 0, 0));
        LocalDate localDate = LocalDate.now();
        assertFalse(factor.apply(localDate.toLocalDateTime(new LocalTime(9, 59, 59))));
        assertTrue(factor.apply(localDate.toLocalDateTime(new LocalTime(10, 0, 0))));

        factor = factor.setTodayTriggered();
        assertEquals(factor.getLastTriggerDate(), Optional.of(LocalDate.now()));
        assertFalse(factor.apply(localDate.toLocalDateTime(new LocalTime(10, 0, 1))));

        assertTrue(factor.apply(localDate.plusDays(1).toLocalDateTime(new LocalTime(10, 0, 0))));
    }
}
