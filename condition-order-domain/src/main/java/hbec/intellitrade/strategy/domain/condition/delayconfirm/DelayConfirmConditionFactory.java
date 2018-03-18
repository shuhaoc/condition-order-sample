package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public enum DelayConfirmConditionFactory {
    /**
     * 单例
     */
    INSTANCE;

    public MarketCondition wrapWith(MarketCondition marketCondition,
                                    DelayConfirm delayConfirm,
                                    int confirmedCount) {
        if (delayConfirm == DisabledDelayConfirm.DISABLED) {
            return marketCondition;
        }

        DelayConfirmInfo delayConfirmInfo = (DelayConfirmInfo) delayConfirm;
        if (delayConfirm.getOption() == DelayConfirmOption.ACCUMULATE) {
            return new AccumulatedDelayConfirmCondition(delayConfirmInfo.getConfirmTimes(),
                                                        confirmedCount,
                                                        marketCondition);
        } else if (delayConfirm.getOption() == DelayConfirmOption.CONTINUOUS) {
            return new ContinuousDelayConfirmCondition(delayConfirmInfo.getConfirmTimes(),
                                                       confirmedCount,
                                                       marketCondition);
        } else {
            throw new IllegalArgumentException("Illegal delay confirm parameter: " + delayConfirm);
        }
    }
}
