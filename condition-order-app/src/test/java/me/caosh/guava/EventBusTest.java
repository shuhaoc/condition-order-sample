package me.caosh.guava;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.junit.Test;

import java.util.List;

/**
 * @author caoshuhao@touker.com
 * @date 2017/12/25
 */
public class EventBusTest {
    @Test
    public void testNoException() throws Exception {
        EventBus eventBus = new EventBus();
        eventBus.register(new Object() {
            @Subscribe
            public void onEvent(Object any) {
                throw new IllegalArgumentException(String.valueOf(any));
            }
        });
        eventBus.post(123);
    }

    public static class ExceptionThrownEventBus {
        private List<RuntimeException> throwableList = Lists.newArrayList();
        private EventBus eventBus;

        public ExceptionThrownEventBus() {
            eventBus = new EventBus(new SubscriberExceptionHandler() {
                @Override
                public void handleException(Throwable exception, SubscriberExceptionContext context) {
                    if (exception instanceof RuntimeException) {
                        throwableList.add((RuntimeException) exception);
                    } else {
                        throwableList.add(new RuntimeException(exception));
                    }
                }
            });
        }

        public void register(Object object) {
            eventBus.register(object);
        }

        public void post(Object event) {
            eventBus.post(event);
            if (!throwableList.isEmpty()) {
                throw throwableList.get(0);
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void testThrowsException() throws Exception {
        ExceptionThrownEventBus eventBus = new ExceptionThrownEventBus();
        eventBus.register(new Object() {
            @Subscribe
            public void onEvent(Object any) throws Exception {
                throw new Exception(String.valueOf(any));
            }
        });
        eventBus.post(123);
    }
}
