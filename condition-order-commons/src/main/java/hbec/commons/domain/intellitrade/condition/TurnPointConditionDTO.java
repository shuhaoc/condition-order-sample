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
    private BigDecimal turnBackPercent;
    private Integer binaryFactorType;
    private BigDecimal turnBackIncrement;
    private boolean useGuaranteedPrice;
    private boolean broken;
    private BigDecimal extremePrice;

    public TurnPointConditionDTO() {
    }

    public TurnPointConditionDTO(CompareOperator compareOperator,
                                 BigDecimal breakPrice,
                                 BigDecimal turnBackPercent,
                                 Integer binaryFactorType,
                                 BigDecimal turnBackIncrement,
                                 boolean useGuaranteedPrice,
                                 boolean broken,
                                 BigDecimal extremePrice) {
        this.compareOperator = compareOperator;
        this.breakPrice = breakPrice;
        this.turnBackPercent = turnBackPercent;
        this.binaryFactorType = binaryFactorType;
        this.turnBackIncrement = turnBackIncrement;
        this.useGuaranteedPrice = useGuaranteedPrice;
        this.broken = broken;
        this.extremePrice = extremePrice;
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

    public BigDecimal getTurnBackPercent() {
        return turnBackPercent;
    }

    public void setTurnBackPercent(BigDecimal turnBackPercent) {
        this.turnBackPercent = turnBackPercent;
    }

    public Integer getBinaryFactorType() {
        return binaryFactorType;
    }

    public void setBinaryFactorType(Integer binaryFactorType) {
        this.binaryFactorType = binaryFactorType;
    }

    public BigDecimal getTurnBackIncrement() {
        return turnBackIncrement;
    }

    public void setTurnBackIncrement(BigDecimal turnBackIncrement) {
        this.turnBackIncrement = turnBackIncrement;
    }

    public boolean isUseGuaranteedPrice() {
        return useGuaranteedPrice;
    }

    public void setUseGuaranteedPrice(boolean useGuaranteedPrice) {
        this.useGuaranteedPrice = useGuaranteedPrice;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
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
        return MoreObjects.toStringHelper(TurnPointConditionDTO.class).omitNullValues()
                          .add("compareOperator", compareOperator)
                          .add("breakPrice", breakPrice)
                          .add("turnBackPercent", turnBackPercent)
                          .add("binaryFactorType", binaryFactorType)
                          .add("turnBackIncrement", turnBackIncrement)
                          .add("useGuaranteedPrice", useGuaranteedPrice)
                          .add("broken", broken)
                          .add("extremePrice", extremePrice)
                          .toString();
    }
}
