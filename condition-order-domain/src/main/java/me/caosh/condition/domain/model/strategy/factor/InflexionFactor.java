package me.caosh.condition.domain.model.strategy.factor;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.strategy.shared.DirtyFlag;
import me.caosh.condition.domain.model.strategy.shared.DynamicProperty;

import java.math.BigDecimal;

/**
 * 拐点因子，有动态属性
 * <p>
 * 使用因子作为构件而不是具体的价格可以提供高扩展性
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/30
 */
public class InflexionFactor implements TargetPriceFactor, DirtyFlag {
    /**
     * 突破价因子
     */
    private final TargetPriceFactor breakPriceFactor;

    /**
     * 折返价因子
     * <p>
     * 由于折返是相对极值的，极值又是动态的，因此使用二元因子
     */
    private final BinaryTargetPriceFactor turnBackBinaryPriceFactor;

    /**
     * 突破状态
     */
    private final DynamicProperty<Boolean> broken;

    /**
     * 价格极值（最高或最低价）
     */
    private final DynamicProperty<BigDecimal> extremePrice;

    public InflexionFactor(TargetPriceFactor breakPriceFactor, BinaryTargetPriceFactor turnBackBinaryPriceFactor) {
        this.breakPriceFactor = breakPriceFactor;
        this.turnBackBinaryPriceFactor = turnBackBinaryPriceFactor;
        this.broken = new DynamicProperty<>(false);
        this.extremePrice = new DynamicProperty<>();
    }

    public InflexionFactor(TargetPriceFactor breakPriceFactor,
                           BinaryTargetPriceFactor turnBackBinaryPriceFactor,
                           boolean broken,
                           BigDecimal extremePrice) {
        this.breakPriceFactor = breakPriceFactor;
        this.turnBackBinaryPriceFactor = turnBackBinaryPriceFactor;
        this.broken = new DynamicProperty<>(broken);
        this.extremePrice = new DynamicProperty<>(extremePrice);
    }

    @Override
    public boolean apply(BigDecimal price) {
        if (!broken.get()) {
            // 未突破状态
            if (breakPriceFactor.apply(price)) {
                // 突破条件达成
                extremePrice.set(price);
                broken.set(true);
            }
            // 突破条件未达成
            return false;
        } else {
            // 已突破状态
            // 突破价因子的方向即是极值继续更新的方向
            CompareOperator continuousCompareOperator = getContinuousCompareOperator();
            if (continuousCompareOperator.apply(price, extremePrice.get())) {
                // 极值继续更新
                extremePrice.set(price);
                return false;
            } else {
                // 拐点达成条件
                PriceFactor priceFactor = getTurnBackTargetPriceFactor();
                return priceFactor.apply(price);
            }
        }
    }

    private CompareOperator getContinuousCompareOperator() {
        return CompareOperators.nonEquals(breakPriceFactor.getCompareOperator());
    }

    public TargetPriceFactor getBreakPriceFactor() {
        return breakPriceFactor;
    }

    public BinaryTargetPriceFactor getTurnBackBinaryPriceFactor() {
        return turnBackBinaryPriceFactor;
    }

    private TargetPriceFactor getTurnBackTargetPriceFactor() {
        return new BinaryWrapperTargetPriceFactor(turnBackBinaryPriceFactor, extremePrice.get());
    }

    /**
     * 是否已经突破关键价
     *
     * @return 已突破状态
     */
    public boolean isBroken() {
        return broken.get();
    }

    /**
     * 获取极值价
     *
     * @return 极值价
     */
    public Optional<BigDecimal> getExtremePrice() {
        return Optional.fromNullable(extremePrice.get());
    }

    @Override
    public CompareOperator getCompareOperator() {
        return turnBackBinaryPriceFactor.getCompareOperator();
    }

    @Override
    public BigDecimal getTargetPrice() {
        Preconditions.checkArgument(broken.get() && extremePrice.get() != null,
                "Target price is not available until broken");
        TargetPriceFactor priceFactor = getTurnBackTargetPriceFactor();
        return priceFactor.getTargetPrice();
    }

    @Override
    public boolean isDirty() {
        return broken.isDirty() || extremePrice.isDirty();
    }

    @Override
    public void clearDirty() {
        broken.clearDirty();
        extremePrice.clearDirty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InflexionFactor that = (InflexionFactor) o;

        if (!breakPriceFactor.equals(that.breakPriceFactor)) {
            return false;
        }
        if (!turnBackBinaryPriceFactor.equals(that.turnBackBinaryPriceFactor)) {
            return false;
        }
        if (!broken.equals(that.broken)) {
            return false;
        }
        return extremePrice.equals(that.extremePrice);
    }

    @Override
    public int hashCode() {
        int result = breakPriceFactor.hashCode();
        result = 31 * result + turnBackBinaryPriceFactor.hashCode();
        result = 31 * result + broken.hashCode();
        result = 31 * result + extremePrice.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(InflexionFactor.class).omitNullValues()
                .addValue(breakPriceFactor)
                .addValue(turnBackBinaryPriceFactor)
                .addValue(broken.get() ? getTurnBackTargetPriceFactor() : null)
                .addValue(broken)
                .addValue(extremePrice)
                .toString();
    }
}
