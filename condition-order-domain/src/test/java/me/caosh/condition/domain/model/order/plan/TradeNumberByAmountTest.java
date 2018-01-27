package me.caosh.condition.domain.model.order.plan;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/17.
 */
public class TradeNumberByAmountTest {
    @Test
    public void test() throws Exception {
        TradeNumber tradeNumber = new TradeNumberByAmount(new BigDecimal("10000"));
        assertEquals(1000, tradeNumber.getNumber(BigDecimal.valueOf(10)));
        assertEquals(900, tradeNumber.getNumber(BigDecimal.valueOf(11)));
    }
}
