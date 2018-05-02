package me.caosh.condition.infrastructure.tunnel.model;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.conditionorder.domain.orders.grid.DecoratedGridCondition;
import hbec.intellitrade.conditionorder.domain.orders.grid.DecoratedGridConditionBuilder;
import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.MappedClass;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/15.
 */
@MappedClass(value = DecoratedGridCondition.class, builderClass = DecoratedGridConditionBuilder.class)
public class GridConditionDO implements ConditionDO {

    private BigDecimal basePrice;

    private Integer binaryFactorType;

    @FieldMapping(mappedProperty = "sellCondition.mainFactor.percent")
    private BigDecimal increasePercent;

    @FieldMapping(mappedProperty = "sellCondition.turnBackFactor.percent")
    private BigDecimal fallPercent;

    @FieldMapping(mappedProperty = "sellCondition.mainFactor.increment")
    private BigDecimal increaseIncrement;

    @FieldMapping(mappedProperty = "sellCondition.turnBackFactor.increment")
    private BigDecimal fallIncrement;

    @FieldMapping(mappedProperty = "buyCondition.mainFactor.percent")
    private BigDecimal decreasePercent;

    @FieldMapping(mappedProperty = "buyCondition.turnBackFactor.percent")
    private BigDecimal reboundPercent;

    @FieldMapping(mappedProperty = "buyCondition.mainFactor.increment")
    private BigDecimal decreaseIncrement;

    @FieldMapping(mappedProperty = "buyCondition.turnBackFactor.increment")
    private BigDecimal reboundIncrement;

    private Boolean useGuaranteedPrice;

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getBinaryFactorType() {
        return binaryFactorType;
    }

    public void setBinaryFactorType(Integer binaryFactorType) {
        this.binaryFactorType = binaryFactorType;
    }

    public BigDecimal getIncreasePercent() {
        return increasePercent;
    }

    public void setIncreasePercent(BigDecimal increasePercent) {
        this.increasePercent = increasePercent;
    }

    public BigDecimal getFallPercent() {
        return fallPercent;
    }

    public void setFallPercent(BigDecimal fallPercent) {
        this.fallPercent = fallPercent;
    }

    public BigDecimal getIncreaseIncrement() {
        return increaseIncrement;
    }

    public void setIncreaseIncrement(BigDecimal increaseIncrement) {
        this.increaseIncrement = increaseIncrement;
    }

    public BigDecimal getFallIncrement() {
        return fallIncrement;
    }

    public void setFallIncrement(BigDecimal fallIncrement) {
        this.fallIncrement = fallIncrement;
    }

    public BigDecimal getDecreasePercent() {
        return decreasePercent;
    }

    public void setDecreasePercent(BigDecimal decreasePercent) {
        this.decreasePercent = decreasePercent;
    }

    public BigDecimal getReboundPercent() {
        return reboundPercent;
    }

    public void setReboundPercent(BigDecimal reboundPercent) {
        this.reboundPercent = reboundPercent;
    }

    public BigDecimal getDecreaseIncrement() {
        return decreaseIncrement;
    }

    public void setDecreaseIncrement(BigDecimal decreaseIncrement) {
        this.decreaseIncrement = decreaseIncrement;
    }

    public BigDecimal getReboundIncrement() {
        return reboundIncrement;
    }

    public void setReboundIncrement(BigDecimal reboundIncrement) {
        this.reboundIncrement = reboundIncrement;
    }

    public Boolean getUseGuaranteedPrice() {
        return useGuaranteedPrice;
    }

    public void setUseGuaranteedPrice(Boolean useGuaranteedPrice) {
        this.useGuaranteedPrice = useGuaranteedPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(GridConditionDO.class).omitNullValues()
                .add("basePrice", basePrice)
                .add("binaryFactorType", binaryFactorType)
                .add("increasePercent", increasePercent)
                .add("fallPercent", fallPercent)
                .add("increaseIncrement", increaseIncrement)
                .add("fallIncrement", fallIncrement)
                .add("decreasePercent", decreasePercent)
                .add("reboundPercent", reboundPercent)
                .add("decreaseIncrement", decreaseIncrement)
                .add("reboundIncrement", reboundIncrement)
                .add("useGuaranteedPrice", useGuaranteedPrice)
                .toString();
    }
}
