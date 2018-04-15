package me.caosh.condition.infrastructure.tunnel.model;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.condorder.domain.orders.turnpoint.DecoratedTurnPointCondition;
import hbec.intellitrade.condorder.domain.orders.turnpoint.DecoratedTurnPointConditionBuilder;
import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.MappedClass;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/15.
 */
@MappedClass(value = DecoratedTurnPointCondition.class, builderClass = DecoratedTurnPointConditionBuilder.class)
public class TurnPointConditionDO implements ConditionDO {
    private Integer compareOperator;
    private BigDecimal breakPrice;
    @FieldMapping(mappedProperty = "turnBackBinaryPriceFactor.binaryFactorType")
    private Integer binaryFactorType;
    @FieldMapping(mappedProperty = "turnBackBinaryPriceFactor.percent")
    private BigDecimal turnBackPercent;
    @FieldMapping(mappedProperty = "turnBackBinaryPriceFactor.increment")
    private BigDecimal turnBackIncrement;
    private Boolean useGuaranteedPrice;
    private BigDecimal baselinePrice;

    // ---------------------------------- Dynamic properties ----------------------------------

    private Boolean broken;
    private BigDecimal extremePrice;

    public Integer getCompareOperator() {
        return compareOperator;
    }

    public void setCompareOperator(Integer compareOperator) {
        this.compareOperator = compareOperator;
    }

    public BigDecimal getBreakPrice() {
        return breakPrice;
    }

    public void setBreakPrice(BigDecimal breakPrice) {
        this.breakPrice = breakPrice;
    }

    public Integer getBinaryFactorType() {
        return binaryFactorType;
    }

    public void setBinaryFactorType(Integer binaryFactorType) {
        this.binaryFactorType = binaryFactorType;
    }

    public BigDecimal getTurnBackPercent() {
        return turnBackPercent;
    }

    public void setTurnBackPercent(BigDecimal turnBackPercent) {
        this.turnBackPercent = turnBackPercent;
    }

    public BigDecimal getTurnBackIncrement() {
        return turnBackIncrement;
    }

    public void setTurnBackIncrement(BigDecimal turnBackIncrement) {
        this.turnBackIncrement = turnBackIncrement;
    }

    public Boolean getUseGuaranteedPrice() {
        return useGuaranteedPrice;
    }

    public void setUseGuaranteedPrice(Boolean useGuaranteedPrice) {
        this.useGuaranteedPrice = useGuaranteedPrice;
    }

    public BigDecimal getBaselinePrice() {
        return baselinePrice;
    }

    public void setBaselinePrice(BigDecimal baselinePrice) {
        this.baselinePrice = baselinePrice;
    }

    public Boolean getBroken() {
        return broken;
    }

    public void setBroken(Boolean broken) {
        this.broken = broken;
    }

    public BigDecimal getExtremePrice() {
        return extremePrice;
    }

    public void setExtremePrice(BigDecimal extremePrice) {
        this.extremePrice = extremePrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TurnPointConditionDO.class).omitNullValues()
                          .add("compareOperator", compareOperator)
                          .add("breakPrice", breakPrice)
                          .add("binaryFactorType", binaryFactorType)
                          .add("turnBackPercent", turnBackPercent)
                          .add("turnBackIncrement", turnBackIncrement)
                          .add("useGuaranteedPrice", useGuaranteedPrice)
                          .add("baselinePrice", baselinePrice)
                          .add("broken", broken)
                          .add("extremePrice", extremePrice)
                          .toString();
    }
}
