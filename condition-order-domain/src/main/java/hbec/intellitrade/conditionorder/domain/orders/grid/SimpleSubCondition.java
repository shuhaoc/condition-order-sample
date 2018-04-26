package hbec.intellitrade.conditionorder.domain.orders.grid;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.condition.AbstractMarketCondition;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.BinaryWrapperTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 简单的单方向条件（不带拐点）
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
public class SimpleSubCondition extends AbstractMarketCondition implements GridSubCondition {
    private final TargetPriceFactor targetPriceFactor;
    private final BinaryTargetPriceFactor binaryTargetPriceFactor;

    public SimpleSubCondition(BinaryTargetPriceFactor binaryTargetPriceFactor, BigDecimal basePrice) {
        this.targetPriceFactor = new BinaryWrapperTargetPriceFactor(binaryTargetPriceFactor, basePrice);
        this.binaryTargetPriceFactor = binaryTargetPriceFactor;
    }

    @Override
    public GridSubCondition changeBasePrice(BigDecimal basePrice) {
        return new SimpleSubCondition(binaryTargetPriceFactor, basePrice);
    }

    @Override
    public TargetPriceFactor getTargetPriceFactor() {
        return targetPriceFactor;
    }

    public BinaryTargetPriceFactor getBinaryTargetPriceFactor() {
        return binaryTargetPriceFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        SimpleSubCondition that = (SimpleSubCondition) o;
        return Objects.equals(targetPriceFactor, that.targetPriceFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetPriceFactor);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(SimpleSubCondition.class).omitNullValues()
                .add("targetPriceFactor", targetPriceFactor)
                .toString();
    }
}
