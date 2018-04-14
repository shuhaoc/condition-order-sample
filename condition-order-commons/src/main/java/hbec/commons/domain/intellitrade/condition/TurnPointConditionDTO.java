package hbec.commons.domain.intellitrade.condition;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拐点（回落）条件DTO
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/30
 */
public class TurnPointConditionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private CompareOperator compareOperator;
    private BigDecimal breakPrice;
    private Integer binaryFactorType;
    private BigDecimal turnBackPercent;
    private BigDecimal turnBackIncrement;
    private Boolean useGuaranteedPrice;

    // ---------------------------------- Dynamic properties ----------------------------------

    private Boolean broken;
    private BigDecimal extremePrice;
    private Integer turnPointDelayConfirmedCount;
    private Integer crossDelayConfirmedCount;

    public TurnPointConditionDTO() {
    }

    public TurnPointConditionDTO(CompareOperator compareOperator,
                                 BigDecimal breakPrice,
                                 Integer binaryFactorType,
                                 BigDecimal turnBackPercent,
                                 BigDecimal turnBackIncrement,
                                 Boolean useGuaranteedPrice,
                                 Boolean broken,
                                 BigDecimal extremePrice,
                                 Integer turnPointDelayConfirmedCount,
                                 Integer crossDelayConfirmedCount) {
        this.compareOperator = compareOperator;
        this.breakPrice = breakPrice;
        this.binaryFactorType = binaryFactorType;
        this.turnBackPercent = turnBackPercent;
        this.turnBackIncrement = turnBackIncrement;
        this.useGuaranteedPrice = useGuaranteedPrice;
        this.broken = broken;
        this.extremePrice = extremePrice;
        this.turnPointDelayConfirmedCount = turnPointDelayConfirmedCount;
        this.crossDelayConfirmedCount = crossDelayConfirmedCount;
    }

    public CompareOperator getCompareOperator() {
        return compareOperator;
    }

    public void setCompareOperator(CompareOperator compareOperator) {
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

    public Integer getTurnPointDelayConfirmedCount() {
        return turnPointDelayConfirmedCount;
    }

    public void setTurnPointDelayConfirmedCount(Integer turnPointDelayConfirmedCount) {
        this.turnPointDelayConfirmedCount = turnPointDelayConfirmedCount;
    }

    public Integer getCrossDelayConfirmedCount() {
        return crossDelayConfirmedCount;
    }

    public void setCrossDelayConfirmedCount(Integer crossDelayConfirmedCount) {
        this.crossDelayConfirmedCount = crossDelayConfirmedCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TurnPointConditionDTO.class).omitNullValues()
                          .add("compareOperator", compareOperator)
                          .add("breakPrice", breakPrice)
                          .add("binaryFactorType", binaryFactorType)
                          .add("turnBackPercent", turnBackPercent)
                          .add("turnBackIncrement", turnBackIncrement)
                          .add("useGuaranteedPrice", useGuaranteedPrice)
                          .add("broken", broken)
                          .add("extremePrice", extremePrice)
                          .add("turnPointDelayConfirmedCount", turnPointDelayConfirmedCount)
                          .add("crossDelayConfirmedCount", crossDelayConfirmedCount)
                          .toString();
    }
}
