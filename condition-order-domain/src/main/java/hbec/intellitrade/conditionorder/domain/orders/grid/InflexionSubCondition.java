package hbec.intellitrade.conditionorder.domain.orders.grid;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.condition.AbstractMarketCondition;
import hbec.intellitrade.strategy.domain.condition.DynamicCondition;
import hbec.intellitrade.strategy.domain.factor.BinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.BinaryWrapperTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.InflexionFactor;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 带拐点的单方向条件
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
public class InflexionSubCondition extends AbstractMarketCondition implements GridSubCondition, DynamicCondition {
    private final InflexionFactor inflexionFactor;
    private final BinaryTargetPriceFactor mainFactor;
    private final BinaryTargetPriceFactor turnBackFactor;

    public InflexionSubCondition(BinaryTargetPriceFactor mainFactor,
            BinaryTargetPriceFactor turnBackFactor,
            BigDecimal basePrice,
            boolean useGuaranteedPrice) {
        this.inflexionFactor = new InflexionFactor(new BinaryWrapperTargetPriceFactor(mainFactor, basePrice),
                turnBackFactor,
                useGuaranteedPrice);
        this.mainFactor = mainFactor;
        this.turnBackFactor = turnBackFactor;
    }

    public InflexionSubCondition(BinaryTargetPriceFactor mainFactor,
            BinaryTargetPriceFactor turnBackFactor,
            BigDecimal basePrice,
            boolean useGuaranteedPrice,
            boolean broken,
            BigDecimal extremePrice) {
        this.inflexionFactor = new InflexionFactor(new BinaryWrapperTargetPriceFactor(mainFactor, basePrice),
                turnBackFactor,
                useGuaranteedPrice,
                broken,
                extremePrice);
        this.mainFactor = mainFactor;
        this.turnBackFactor = turnBackFactor;
    }

    @Override
    public GridSubCondition changeBasePrice(BigDecimal basePrice) {
        InflexionFactor inflexionFactor = getInflexionFactor();
        return new InflexionSubCondition(mainFactor,
                turnBackFactor,
                basePrice,
                inflexionFactor.isUseGuaranteedPrice());
    }

    @Override
    public TargetPriceFactor getTargetPriceFactor() {
        return getInflexionFactor();
    }

    public InflexionFactor getInflexionFactor() {
        return inflexionFactor;
    }

    public BinaryTargetPriceFactor getMainFactor() {
        return mainFactor;
    }

    public BinaryTargetPriceFactor getTurnBackFactor() {
        return turnBackFactor;
    }

    @Override
    public boolean isDirty() {return inflexionFactor.isDirty();}

    @Override
    public void clearDirty() {inflexionFactor.clearDirty();}

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        InflexionSubCondition that = (InflexionSubCondition) o;
        return Objects.equals(inflexionFactor, that.inflexionFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inflexionFactor);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(InflexionSubCondition.class).omitNullValues()
                .add("inflexionFactor", inflexionFactor)
                .toString();
    }
}
