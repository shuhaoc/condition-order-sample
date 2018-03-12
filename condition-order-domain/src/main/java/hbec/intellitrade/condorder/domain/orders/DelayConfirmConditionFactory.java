package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.strategy.domain.condition.delayconfirm.AccumulatedDelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.ContinuousDelayConfirmCondition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirmParam;
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

    public MarketCondition wrapWith(PriceCondition priceCondition, DelayConfirmParam delayConfirmParam) {
        if (delayConfirmParam == DisabledDelayConfirmParam.INSTANCE) {
            return priceCondition;
        } else if (delayConfirmParam.getOption() == DelayConfirmOption.ACCUMULATE) {
            return new AccumulatedDelayConfirmCondition(delayConfirmParam.getConfirmTimes(), priceCondition);
        } else if (delayConfirmParam.getOption() == DelayConfirmOption.CONTINUOUS) {
            return new ContinuousDelayConfirmCondition(delayConfirmParam.getConfirmTimes(), priceCondition);
        } else {
            throw new IllegalArgumentException("Illegal delay confirm parameter: " + delayConfirmParam);
        }
    }
}
