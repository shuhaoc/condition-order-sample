package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.strategy.domain.condition.DynamicCondition;

/**
 * 延迟确认条件
 * <p>
 * 对于延迟确认条件所包装的行情条件，需要保证“触发不改变，改变不触发”，
 * 即触发交易信号时自身动态属性不应该有所变化，自身属性有变化时不应该触发交易信号
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
