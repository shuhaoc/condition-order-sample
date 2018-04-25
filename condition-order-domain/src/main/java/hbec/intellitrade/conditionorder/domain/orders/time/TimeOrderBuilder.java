package hbec.intellitrade.conditionorder.domain.orders.time;

import hbec.intellitrade.conditionorder.domain.orders.ConditionOrderBuilder;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/25
 */
public class TimeOrderBuilder extends ConditionOrderBuilder {
    public TimeOrderBuilder() {
        getStrategyInfo().setStrategyType(NativeStrategyInfo.TIME);
        setCondition(new TimeReachedConditionBuilder());
    }

    @Override
    public TimeOrder build() {
        return (TimeOrder) super.build();
    }
}
