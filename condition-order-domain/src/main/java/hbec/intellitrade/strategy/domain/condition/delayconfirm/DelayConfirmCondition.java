package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.strategy.domain.condition.DynamicCondition;

/**
 * 延迟确认条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/20
 */
public interface DelayConfirmCondition extends DynamicCondition {
    /**
     * 获取当前已确认次数
     *
     * @return 当前已确认次数
     */
    int getDelayConfirmedCount();

    /**
     * 重置延迟确认计数器
     */
    void resetCounter();
}
