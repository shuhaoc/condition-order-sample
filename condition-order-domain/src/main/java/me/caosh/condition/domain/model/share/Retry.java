package me.caosh.condition.domain.model.share;

/**
 * Created by caosh on 2017/2/6.
 *
 * @author caoshuhao@touker.com
 */
public class Retry {
    public interface RetryAction<T> {
        T onTry() throws Exception;

        void onFailedOnce(Exception e, int retriedTimes);

        void onTriedOver(Exception e) throws Exception;

        void onUnexpectedException(Exception e) throws Exception;
    }

    public static abstract class BaseRetryAction<T> implements RetryAction<T> {
        @Override
        public void onFailedOnce(Exception e, int retriedTimes) {
        }

        @Override
        public void onTriedOver(Exception e) throws Exception {
            throw e;
        }

        @Override
        public void onUnexpectedException(Exception e) throws Exception {
            throw e;
        }
    }

    private int times;
    private Class<? extends Exception> retryExceptionClass = Exception.class;

    private Retry() {
    }

    private Retry setTimes(int times) {
        this.times = times;
        return this;
    }

    public static Retry times(int times) {
        return new Retry().setTimes(times);
    }

    public Retry onException(Class<? extends Exception> exceptionClass) {
        this.retryExceptionClass = exceptionClass;
        return this;
    }

    public static class UnhandledException extends Exception {
    }

    public <T> T execute(RetryAction<T> retryAction) throws Exception {
        for (int i = 0; i < times; i++) {
            try {
                return retryAction.onTry();
            } catch (Exception e) {
                if (retryExceptionClass.isAssignableFrom(e.getClass())) {
                    if (i == times - 1) {
                        // last time
                        retryAction.onTriedOver(e);
                    } else {
                        retryAction.onFailedOnce(e, i + 1);
                        // continue;
                    }
                } else {
                    retryAction.onUnexpectedException(e);
                }
            }
        }
        throw new UnhandledException();
    }
}
