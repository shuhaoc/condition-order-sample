package hbec.intellitrade.condorder.domain.tradeplan;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/17.
 */
public class TradeNumberByAmountTest {
    @Test
    public void test() throws Exception {
        TradeNumberByAmount tradeNumber = new TradeNumberByAmount(new BigDecimal("10000"));
        assertEquals(tradeNumber.getNumber(), new BigDecimal("10000"));
        assertEquals(tradeNumber.getAmount(), new BigDecimal("10000"));

        assertEquals(1000, tradeNumber.getNumber(BigDecimal.valueOf(10)));
        assertEquals(900, tradeNumber.getNumber(BigDecimal.valueOf(11)));
    }
}
