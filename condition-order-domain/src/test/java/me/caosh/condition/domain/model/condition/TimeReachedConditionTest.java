package me.caosh.condition.domain.model.condition;

import hbec.intellitrade.strategy.domain.signal.Signals;
import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class TimeReachedConditionTest {
    @Test
    public void test() throws Exception {
        TimeReachedCondition timeReachedCondition = new TimeReachedCondition(
                new LocalDateTime(2018, 1, 30, 14, 0, 0)
        );

        assertTrue(timeReachedCondition.onTimeGoesBy(new LocalDateTime(2018, 1, 30, 14, 0, 0)));

        assertEquals(timeReachedCondition.getTargetTime(), new LocalDateTime(2018, 1, 30, 14, 0, 0));
        assertEquals(timeReachedCondition, new TimeReachedCondition(
                new LocalDateTime(2018, 1, 30, 14, 0, 0)
        ));
        System.out.println(timeReachedCondition.hashCode());
        System.out.println(timeReachedCondition);
    }

    @Test
    public void testOnSecondTick() throws Exception {
        TimeReachedCondition timeReachedCondition1 = new TimeReachedCondition(LocalDateTime.now());
        assertEquals(timeReachedCondition1.onTimeTick(), Signals.buyOrSell());

        TimeReachedCondition timeReachedCondition2 = new TimeReachedCondition(LocalDateTime.now().plusMinutes(1));
        assertEquals(timeReachedCondition2.onTimeTick(), Signals.none());
    }
}
