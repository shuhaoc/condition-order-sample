package me.caosh.condition.infrastructure.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by caosh on 2017/8/16.
 *
 * @author caoshuhao@touker.com
 */
public class MonitorEventBus {
    public static final AsyncEventBus EVENT_SERIALIZED = new AsyncEventBus(new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
            new CustomizableThreadFactory("MonitorThread-")));
}
