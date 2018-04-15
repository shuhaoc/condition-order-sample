package hbec.intellitrade.strategy.domain.factor;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import hbec.intellitrade.strategy.domain.factor.logic.OrTargetPriceFactor;
import hbec.intellitrade.strategy.domain.shared.DirtyFlag;
import hbec.intellitrade.strategy.domain.shared.DynamicProperty;

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
     * 保底价（即突破价）触发，即折返至突破价是否满足条件
     */
    private final boolean useGuaranteedPrice;

    /**
     * 突破状态
     */
    private final DynamicProperty<Boolean> broken;

    /**
     * 价格极值（最高或最低价）
     */
    private final DynamicProperty<BigDecimal> extremePrice;

    public InflexionFactor(TargetPriceFactor breakPriceFactor,
                           BinaryTargetPriceFactor turnBackBinaryPriceFactor,
                           boolean useGuaranteedPrice) {
        this(breakPriceFactor, turnBackBinaryPriceFactor, useGuaranteedPrice, false, null);
    }

    public InflexionFactor(TargetPriceFactor breakPriceFactor,
                           BinaryTargetPriceFactor turnBackBinaryPriceFactor,
                           boolean useGuaranteedPrice,
                           boolean broken,
                           BigDecimal extremePrice) {
        this.breakPriceFactor = breakPriceFactor;
        this.turnBackBinaryPriceFactor = turnBackBinaryPriceFactor;
        this.useGuaranteedPrice = useGuaranteedPrice;
        this.broken = new DynamicProperty<>(broken);

        if (broken) {
            Preconditions.checkNotNull(extremePrice, "extremePrice cannot be null when broken");
        }
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
                Factor<BigDecimal> priceFactor = getTurnBackTargetPriceFactor();
                return priceFactor.apply(price);
            }
        }
    }

    private CompareOperator getContinuousCompareOperator() {
        return breakPriceFactor.getCompareOperator().withoutEquals();
    }

    public TargetPriceFactor getBreakPriceFactor() {
        return breakPriceFactor;
    }

    public BinaryTargetPriceFactor getTurnBackBinaryPriceFactor() {
        return turnBackBinaryPriceFactor;
    }

    private TargetPriceFactor getTurnBackTargetPriceFactor() {
        // 原定折返目标价
        BinaryWrapperTargetPriceFactor originTurnBackFactor = new BinaryWrapperTargetPriceFactor(
                turnBackBinaryPriceFactor,
                extremePrice.get());

        if (!useGuaranteedPrice) {
            return originTurnBackFactor;
        }

        // 保底价因子
        TargetPriceFactor guranteedPriceFactor = new BasicTargetPriceFactor(
                breakPriceFactor.getCompareOperator().reverse(),
                breakPriceFactor.getTargetPrice());
        // 折返至突破价或原定折返目标价都满足条件
        return new OrTargetPriceFactor(originTurnBackFactor, guranteedPriceFactor);
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

    public boolean isUseGuaranteedPrice() {
        return useGuaranteedPrice;
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

        if (useGuaranteedPrice != that.useGuaranteedPrice) {
            return false;
        }
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
        result = 31 * result + (useGuaranteedPrice ? 1 : 0);
        result = 31 * result + broken.hashCode();
        result = 31 * result + extremePrice.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(InflexionFactor.class).omitNullValues()
                          .add("breakPriceFactor", breakPriceFactor)
                          .add("turnBackBinaryPriceFactor", turnBackBinaryPriceFactor)
                          .add("useGuaranteedPrice", useGuaranteedPrice)
                          .add("broken", broken)
                          .add("extremePrice", extremePrice)
                          .toString();
    }
}
