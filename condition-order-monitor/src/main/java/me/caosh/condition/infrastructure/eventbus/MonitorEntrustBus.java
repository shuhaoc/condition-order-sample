package me.caosh.condition.infrastructure.eventbus;

import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.Executors;

/**
 * Created by caosh on 2017/8/16.
 *
 * @author caoshuhao@touker.com
 */
public class MonitorEntrustBus {
    public static final AsyncEventBus EVENT_SERIALIZED = new AsyncEventBus(Executors.newSingleThreadExecutor());
}
