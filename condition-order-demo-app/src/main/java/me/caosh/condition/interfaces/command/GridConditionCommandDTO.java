package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.condition.ConditionDTO;
import hbec.intellitrade.conditionorder.domain.orders.grid.DecoratedGridCondition;
import hbec.intellitrade.conditionorder.domain.orders.grid.DecoratedGridConditionBuilder;
import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.MappedClass;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/5/2
 */
@MappedClass(value = DecoratedGridCondition.class, builderClass = DecoratedGridConditionBuilder.class)
public class GridConditionCommandDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    private BigDecimal basePrice;

    @NotNull
    @Range(min = 0, max = 1)
    private Integer binaryFactorType;

    @DecimalMin("0.01")
    @FieldMapping(mappedProperty = "sellCondition.breakPercent")
    private BigDecimal increasePercent;

    @DecimalMax("0")
    @FieldMapping(mappedProperty = "sellCondition.turnBackPercent")
    private BigDecimal fallPercent;

    @DecimalMin("0.001")
    @FieldMapping(mappedProperty = "sellCondition.breakIncrement")
    private BigDecimal increaseIncrement;

    @DecimalMax("0")
    @FieldMapping(mappedProperty = "sellCondition.turnBackIncrement")
    private BigDecimal fallIncrement;

    @DecimalMax("-0.01")
    @FieldMapping(mappedProperty = "buyCondition.breakPercent")
    private BigDecimal decreasePercent;

    @DecimalMin("0")
    @FieldMapping(mappedProperty = "buyCondition.turnBackPercent")
    private BigDecimal reboundPercent;

    @DecimalMax("-0.001")
    @FieldMapping(mappedProperty = "buyCondition.breakIncrement")
    private BigDecimal decreaseIncrement;

    @DecimalMin("0")
    @FieldMapping(mappedProperty = "buyCondition.turnBackIncrement")
    private BigDecimal reboundIncrement;

    @NotNull
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
        return MoreObjects.toStringHelper(GridConditionCommandDTO.class).omitNullValues()
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
