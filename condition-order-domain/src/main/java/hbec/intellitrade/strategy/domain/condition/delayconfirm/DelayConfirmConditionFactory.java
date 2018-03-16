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
                                    DelayConfirmParam delayConfirmParam,
                                    int confirmedCount) {
        if (delayConfirmParam == DisabledDelayConfirmParam.DISABLED) {
            return marketCondition;
        } else if (delayConfirmParam.getOption() == DelayConfirmOption.ACCUMULATE) {
            return new AccumulatedDelayConfirmCondition(delayConfirmParam.getConfirmTimes(),
                                                        confirmedCount,
                                                        marketCondition);
        } else if (delayConfirmParam.getOption() == DelayConfirmOption.CONTINUOUS) {
            return new ContinuousDelayConfirmCondition(delayConfirmParam.getConfirmTimes(),
                                                       confirmedCount,
                                                       marketCondition);
        } else {
            throw new IllegalArgumentException("Illegal delay confirm parameter: " + delayConfirmParam);
        }
    }
}
