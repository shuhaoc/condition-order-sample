package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/19.
 */
public class TurnUpConditionDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    private BigDecimal breakPrice;
    private BigDecimal turnUpPercent;
    private Boolean broken;
    private BigDecimal lowestPrice;

    public TurnUpConditionDTO() {
    }

    public TurnUpConditionDTO(BigDecimal breakPrice, BigDecimal turnUpPercent, Boolean broken, BigDecimal lowestPrice) {
        this.breakPrice = breakPrice;
        this.turnUpPercent = turnUpPercent;
        this.broken = broken;
        this.lowestPrice = lowestPrice;
    }

    public BigDecimal getBreakPrice() {
        return breakPrice;
    }

    public void setBreakPrice(BigDecimal breakPrice) {
        this.breakPrice = breakPrice;
    }

    public BigDecimal getTurnUpPercent() {
        return turnUpPercent;
    }

    public void setTurnUpPercent(BigDecimal turnUpPercent) {
        this.turnUpPercent = turnUpPercent;
    }

    public Boolean getBroken() {
        return broken;
    }

    public void setBroken(Boolean broken) {
        this.broken = broken;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("breakPrice", breakPrice)
                .add("turnUpPercent", turnUpPercent)
                .add("broken", broken)
                .add("lowestPrice", lowestPrice)
                .toString();
    }

}

