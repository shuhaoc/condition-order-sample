package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * Created by caosh on 2017/8/17.
 */
public class TradeNumberByAmountTest {
    private static final SecurityInfo SECURITY_INFO = new SecurityInfo(SecurityType.STOCK,
                                                                       "600000",
                                                                       SecurityExchange.SH,
                                                                       "浦发银行");

    @Test
    public void test() throws Exception {
        TradeNumberByAmount tradeNumber = new TradeNumberByAmount(new BigDecimal("10000"));
        assertEquals(tradeNumber.getNumber(), new BigDecimal("10000"));
        assertEquals(tradeNumber.getAmount(), new BigDecimal("10000"));

        assertEquals(tradeNumber.getNumber(SECURITY_INFO, BigDecimal.valueOf(10)),900);
        assertEquals(tradeNumber.getNumber(SECURITY_INFO, BigDecimal.valueOf(12)),800);
    }
}
