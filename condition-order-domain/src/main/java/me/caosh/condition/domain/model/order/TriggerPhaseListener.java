package me.caosh.condition.domain.model.order;

/**
 * Created by caosh on 2017/9/4.
 *
 * @author caoshuhao@touker.com
 */
public interface TriggerPhaseListener {
    void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext);
}
