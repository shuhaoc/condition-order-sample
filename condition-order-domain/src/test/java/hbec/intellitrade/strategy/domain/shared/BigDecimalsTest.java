package hbec.intellitrade.strategy.domain.shared;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/18
 */
public class BigDecimalsTest {
    @Test
    public void test() throws Exception {
        assertEquals(BigDecimals.max(new BigDecimal("1"), new BigDecimal("2")), new BigDecimal("2"));
        assertEquals(BigDecimals.max(new BigDecimal("1"), new BigDecimal("1.00")), new BigDecimal("1"));
        assertEquals(BigDecimals.min(new BigDecimal("1"), new BigDecimal("2")), new BigDecimal("1"));
        assertEquals(BigDecimals.min(new BigDecimal("1"), new BigDecimal("1.00")), new BigDecimal("1"));
    }
}
