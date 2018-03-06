package me.caosh.condition.domain.model.strategy.condition.deviation;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;
import me.caosh.condition.domain.model.order.ConditionVisitor;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.market.PredictableMarketCondition;

/**
 * 禁用状态下的偏差控制条件包装类
 * <p>
 * 直接将调用转发给包装的条件
 *
 * @author caoshuhao@touker.com
 * @date 2018/2/4
 */
public class DisabledDeviationCtrlCondition implements DeviationCtrlCondition, PredictableMarketCondition {
    private final PredictableMarketCondition predictableMarketCondition;

    public DisabledDeviationCtrlCondition(PredictableMarketCondition predictableMarketCondition) {
        this.predictableMarketCondition = predictableMarketCondition;
    }

    @Override
    public TargetPriceFactor getTargetPriceFactor() {
        return predictableMarketCondition.getTargetPriceFactor();
    }

    @Override
    public TradeSignal onMarketUpdate(RealTimeMarket realTimeMarket) {
        return predictableMarketCondition.onMarketUpdate(realTimeMarket);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        predictableMarketCondition.accept(visitor);
    }
}
