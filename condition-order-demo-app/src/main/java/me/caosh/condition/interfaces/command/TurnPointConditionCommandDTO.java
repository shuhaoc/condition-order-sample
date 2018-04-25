package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.condition.ConditionDTO;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.DecoratedTurnPointCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.DecoratedTurnPointConditionBuilder;
import me.caosh.autoasm.FieldMapping;
import me.caosh.autoasm.MappedClass;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@MappedClass(value = DecoratedTurnPointCondition.class, builderClass = DecoratedTurnPointConditionBuilder.class)
public class TurnPointConditionCommandDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Range(min = 0, max = 1)
    private Integer compareOperator;
    @NotNull
    @DecimalMin("0")
    private BigDecimal breakPrice;
    @FieldMapping(mappedProperty = "turnBackBinaryPriceFactor.binaryFactorType")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer binaryFactorType;
    @FieldMapping(mappedProperty = "turnBackBinaryPriceFactor.percent")
    private BigDecimal turnBackPercent;
    @FieldMapping(mappedProperty = "turnBackBinaryPriceFactor.increment")
    private BigDecimal turnBackIncrement;
    @NotNull
    private Boolean useGuaranteedPrice;

    private BigDecimal baselinePrice;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TurnPointConditionCommandDTO.class).omitNullValues()
                          .add("compareOperator", compareOperator)
                          .add("breakPrice", breakPrice)
                          .add("binaryFactorType", binaryFactorType)
                          .add("turnBackPercent", turnBackPercent)
                          .add("turnBackIncrement", turnBackIncrement)
                          .add("useGuaranteedPrice", useGuaranteedPrice)
                          .add("baselinePrice", baselinePrice)
                          .toString();
    }
}
