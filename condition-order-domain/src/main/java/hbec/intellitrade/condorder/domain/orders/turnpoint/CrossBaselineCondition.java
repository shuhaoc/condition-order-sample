package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.factor.BasicTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.strategy.domain.signal.TradeSignal;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 越过底线价条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class CrossBaselineCondition implements MarketCondition {
    private final BasicTargetPriceFactor targetPriceFactor;

    public CrossBaselineCondition(CompareOperator compareOperator, BigDecimal targetPrice) {
        this.targetPriceFactor = new BasicTargetPriceFactor(compareOperator, targetPrice);
    }

    public BigDecimal getTargetPrice() {
        return targetPriceFactor.getTargetPrice();
    }

    @Override
    public TradeSignal onMarketTick(RealTimeMarket realTimeMarket) {
        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        boolean satisfied = targetPriceFactor.apply(currentPrice);
        if (satisfied) {
            return Signals.crossBaseline();
        }
        return Signals.none();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrossBaselineCondition that = (CrossBaselineCondition) o;
        return Objects.equals(targetPriceFactor, that.targetPriceFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetPriceFactor);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(CrossBaselineCondition.class).omitNullValues()
                          .addValue(targetPriceFactor)
                          .toString();
    }
}
