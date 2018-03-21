package hbec.intellitrade.condorder.domain.orders.turnpoint;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.condition.AbstractMarketCondition;
import hbec.intellitrade.strategy.domain.factor.BasicTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 越过底线价条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/21
 */
public class CrossBaselineCondition extends AbstractMarketCondition {
    private final BasicTargetPriceFactor targetPriceFactor;

    public CrossBaselineCondition(CompareOperator compareOperator, BigDecimal targetPrice) {
        this.targetPriceFactor = new BasicTargetPriceFactor(compareOperator, targetPrice);
    }

    @Override
    public TargetPriceFactor getTargetPriceFactor() {
        return targetPriceFactor;
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
