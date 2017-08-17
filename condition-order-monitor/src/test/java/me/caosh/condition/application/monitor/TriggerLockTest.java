package me.caosh.condition.application.monitor;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by caosh on 2017/8/17.
 */
public class TriggerLockTest {
    @Test
    public void test() throws Exception {
        TriggerLock triggerLock = new TriggerLock(1);
        assertTrue(triggerLock.isLocked());

        Thread.sleep(500);
        assertTrue(triggerLock.isLocked());

        Thread.sleep(501);
        assertFalse(triggerLock.isLocked());
    }
}
