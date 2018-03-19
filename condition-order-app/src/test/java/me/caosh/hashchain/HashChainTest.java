package me.caosh.hashchain;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/19
 */
public class HashChainTest {
    @Test
    public void testEquals() throws Exception {
        DemoObject a1 = new DemoObject(0, 1, "600000", "SH", new BigDecimal("12.98"), 100);
        DemoObject a2 = new DemoObject(a1.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.12"), 100);
        DemoObject a3 = new DemoObject(a2.getChainedHash(), 1, "600000", "SH", new BigDecimal("12.99"), 100);
        DemoObject a4 = new DemoObject(a3.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.13"), 100);
        System.out.println(a1.getChainedHash()
                                   + " -> " + a2.getChainedHash()
                                   + " -> " + a3.getChainedHash()
                                   + " -> " + a4.getChainedHash());

        DemoObject b1 = new DemoObject(0, 1, "600000", "SH", new BigDecimal("12.98"), 100);
        DemoObject b2 = new DemoObject(b1.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.12"), 100);
        DemoObject b3 = new DemoObject(b2.getChainedHash(), 1, "600000", "SH", new BigDecimal("12.99"), 100);
        DemoObject b4 = new DemoObject(b3.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.13"), 100);
        System.out.println(b1.getChainedHash()
                                   + " -> " + b2.getChainedHash()
                                   + " -> " + b3.getChainedHash()
                                   + " -> " + b4.getChainedHash());

        assertEquals(a4.getChainedHash(), b4.getChainedHash());
    }

    @Test
    public void testNotEquals() throws Exception {
        DemoObject a1 = new DemoObject(0, 1, "600000", "SH", new BigDecimal("12.98"), 100);
        DemoObject a2 = new DemoObject(a1.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.12"), 100);
        DemoObject a3 = new DemoObject(a2.getChainedHash(), 1, "600000", "SH", new BigDecimal("12.99"), 100);
        DemoObject a4 = new DemoObject(a3.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.13"), 100);
        System.out.println(a1.getChainedHash()
                                   + " -> " + a2.getChainedHash()
                                   + " -> " + a3.getChainedHash()
                                   + " -> " + a4.getChainedHash());

        DemoObject b1 = new DemoObject(0, 1, "600000", "SH", new BigDecimal("12.98"), 100);
        DemoObject b2 = new DemoObject(b1.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.12"), 100);
        DemoObject b3 = new DemoObject(b2.getChainedHash(), 1, "600000", "SH", new BigDecimal("12.98"), 100);
        DemoObject b4 = new DemoObject(b3.getChainedHash(), 2, "600000", "SH", new BigDecimal("13.13"), 100);
        System.out.println(b1.getChainedHash()
                                   + " -> " + b2.getChainedHash()
                                   + " -> " + b3.getChainedHash()
                                   + " -> " + b4.getChainedHash());

        assertNotEquals(a4.getChainedHash(), b4.getChainedHash());
    }
}
