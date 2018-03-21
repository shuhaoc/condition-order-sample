package hbec.intellitrade.condorder.domain.orders.turnpoint;

import hbec.intellitrade.strategy.domain.condition.AbstractMarketCondition;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class CrossBaselineCondition extends AbstractMarketCondition {
    @Override
    public TargetPriceFactor getTargetPriceFactor() {
        return null;
    }
}
