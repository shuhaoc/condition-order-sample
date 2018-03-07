package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/15.
 */
public class TurnUpConditionDO implements ConditionDO {
    private BigDecimal breakPrice;
    private BigDecimal turnUpPercent;

    public TurnUpConditionDO() {
    }

    public TurnUpConditionDO(BigDecimal breakPrice, BigDecimal turnUpPercent) {
        this.breakPrice = breakPrice;
        this.turnUpPercent = turnUpPercent;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("breakPrice", breakPrice)
                .add("turnUpPercent", turnUpPercent)
                .toString();
    }

}
