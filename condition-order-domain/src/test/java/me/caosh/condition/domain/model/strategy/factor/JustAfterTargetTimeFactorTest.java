package me.caosh.condition.domain.model.strategy.factor;

import org.joda.time.LocalDateTime;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class JustAfterTargetTimeFactorTest {
    @Test
    public void test() throws Exception {
        JustAfterTargetTimeFactor timeFactor = new JustAfterTargetTimeFactor(
                new LocalDateTime(2018, 1, 30, 13, 30, 0)
        );

        assertTrue(timeFactor.apply(new LocalDateTime(2018, 1, 30, 13, 30, 0)));
        assertTrue(timeFactor.apply(new LocalDateTime(2018, 1, 30, 13, 31, 0)));

        assertEquals(timeFactor.getTargetTime(), new LocalDateTime(2018, 1, 30, 13, 30, 0));
        assertEquals(timeFactor, new JustAfterTargetTimeFactor(
                new LocalDateTime(2018, 1, 30, 13, 30, 0)
        ));
        System.out.println(timeFactor.hashCode());
        System.out.println(timeFactor);
    }
}
