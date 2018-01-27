package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public abstract class AbstractSignal implements Signal {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
