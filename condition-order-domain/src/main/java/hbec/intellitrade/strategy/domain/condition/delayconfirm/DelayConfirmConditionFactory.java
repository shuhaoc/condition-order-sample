package hbec.intellitrade.strategy.domain.condition.delayconfirm;

import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public enum DelayConfirmConditionFactory {
    /**
     * 单例
     */
    INSTANCE;

    public MarketCondition wrapWith(PriceCondition priceCondition, DelayConfirmParam delayConfirmParam, int confirmedCount) {
        if (delayConfirmParam == DisabledDelayConfirmParam.INSTANCE) {
            return priceCondition;
        } else if (delayConfirmParam.getOption() == DelayConfirmOption.ACCUMULATE) {
            return new AccumulatedDelayConfirmCondition(delayConfirmParam.getConfirmTimes(), confirmedCount, priceCondition);
        } else if (delayConfirmParam.getOption() == DelayConfirmOption.CONTINUOUS) {
            return new ContinuousDelayConfirmCondition(delayConfirmParam.getConfirmTimes(), confirmedCount, priceCondition);
        } else {
            throw new IllegalArgumentException("Illegal delay confirm parameter: " + delayConfirmParam);
        }
    }
}
