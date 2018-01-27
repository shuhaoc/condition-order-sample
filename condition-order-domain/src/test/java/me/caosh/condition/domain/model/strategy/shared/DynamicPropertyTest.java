package me.caosh.condition.domain.model.strategy.shared;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class DynamicPropertyTest {
    @Test
    public void testBasic() throws Exception {
        DynamicProperty<Integer> x = new DynamicProperty<>();
        assertNull(x.get());

        DynamicProperty<Integer> x1 = new DynamicProperty<>(1);
        assertEquals(x1.get().intValue(), 1);

        assertEquals(x1, new DynamicProperty<>(1));
        assertEquals(x1.hashCode(), 1);
        assertEquals(x1.toString(), "DynamicProperty(1)");

        x1.set(2);
        assertEquals(x1.toString(), "DynamicProperty(2)[dirty]");
    }

    @Test
    public void testValue() throws Exception {
        DynamicProperty<Integer> x = new DynamicProperty<>(1);
        assertEquals(x.get().intValue(), 1);
        assertFalse(x.isDirty());

        x.set(2);
        assertEquals(x.get().intValue(), 2);
        assertTrue(x.isDirty());
    }
}
