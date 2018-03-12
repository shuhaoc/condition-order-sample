package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.strategy.domain.shared.DynamicProperty;
import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/8
 */
public class DelayConfirmCounterTest {
    @Test
    public void testBasic() throws Exception {
        DelayConfirmCounter counter = new DelayConfirmCounter(3);
        assertEquals(counter.getConfirmTimes(), 3);
        assertEquals(counter.getConfirmedCount(), 0);
        assertFalse(counter.isConfirmCompleted());
        assertFalse(counter.isDirty());
        System.out.println(counter);

        counter.checkDateChangs();
        counter.increaseConfirmedCount();

        assertEquals(counter.getConfirmTimes(), 3);
        assertEquals(counter.getConfirmedCount(), 1);
        assertFalse(counter.isConfirmCompleted());
        assertTrue(counter.isDirty());
        System.out.println(counter);

        counter.clearDirty();

        counter.checkDateChangs();
        counter.increaseConfirmedCount();
        counter.increaseConfirmedCount();

        assertEquals(counter.getConfirmTimes(), 3);
        assertEquals(counter.getConfirmedCount(), 3);
        assertTrue(counter.isConfirmCompleted());
        System.out.println(counter);
    }

    @Test
    public void testReset() throws Exception {
        DelayConfirmCounter counter = new DelayConfirmCounter(3);
        assertEquals(counter.getConfirmedCount(), 0);
        assertFalse(counter.isConfirmCompleted());
        assertFalse(counter.isDirty());

        counter.checkDateChangs();
        counter.increaseConfirmedCount();

        assertEquals(counter.getConfirmedCount(), 1);
        assertFalse(counter.isConfirmCompleted());
        assertTrue(counter.isDirty());

        counter.clearDirty();

        counter.checkDateChangs();
        counter.reset();

        assertEquals(counter.getConfirmedCount(), 0);
        assertFalse(counter.isConfirmCompleted());
        assertTrue(counter.isDirty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAnotherDay() throws Exception {
        DelayConfirmCounter counter = new DelayConfirmCounter(3);
        counter.increaseConfirmedCount();
        counter.increaseConfirmedCount();

        assertEquals(counter.getConfirmedCount(), 2);
        assertFalse(counter.isConfirmCompleted());

        // 假使过了一天
        Field lastConfirmedDateField = counter.getClass().getDeclaredField("lastConfirmedDate");
        lastConfirmedDateField.setAccessible(true);
        DynamicProperty<LocalDate> lastConfirmedDate = (DynamicProperty<LocalDate>) lastConfirmedDateField.get(counter);
        lastConfirmedDate.set(LocalDate.now().minusDays(1));

        counter.checkDateChangs();
        counter.increaseConfirmedCount();
        assertEquals(counter.getConfirmedCount(), 1);
        assertFalse(counter.isConfirmCompleted());
    }

    @Test
    public void testEquals() throws Exception {
        DelayConfirmCounter counter1 = new DelayConfirmCounter(3);
        counter1.increaseConfirmedCount();

        DelayConfirmCounter counter2 = new DelayConfirmCounter(3);
        counter2.increaseConfirmedCount();

        assertEquals(counter1, counter2);
        assertEquals(counter1.hashCode(), counter2.hashCode());
    }
}
