package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmBuilder;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public interface DelayConfirmSupportedBuilder {
    DelayConfirmBuilder getDelayConfirm();
}
