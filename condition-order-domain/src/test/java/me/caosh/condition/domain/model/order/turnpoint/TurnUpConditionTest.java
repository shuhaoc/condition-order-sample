package me.caosh.condition.domain.model.order.turnpoint;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpConditionTest {
    @Test
    public void test() throws Exception {
        TurnUpCondition turnUpCondition = new TurnUpCondition(BigDecimal.valueOf(100), BigDecimal.valueOf(1));
        assertFalse(turnUpCondition.isSatisfiedBy(new BigDecimal("10.01")));

        assertFalse(turnUpCondition.isSatisfiedBy(new BigDecimal("10.00")));
        assertTrue(turnUpCondition.isDirty());
        turnUpCondition.clearDirty();

        assertFalse(turnUpCondition.isSatisfiedBy(new BigDecimal("10.00")));
        assertFalse(turnUpCondition.isDirty());

        assertFalse(turnUpCondition.isSatisfiedBy(new BigDecimal("9.85"))); // 9.9485
        assertTrue(turnUpCondition.isDirty());
        turnUpCondition.clearDirty();

        assertFalse(turnUpCondition.isSatisfiedBy(new BigDecimal("9.94")));
        assertFalse(turnUpCondition.isDirty());

        assertTrue(turnUpCondition.isSatisfiedBy(new BigDecimal("9.95")));
    }
}
