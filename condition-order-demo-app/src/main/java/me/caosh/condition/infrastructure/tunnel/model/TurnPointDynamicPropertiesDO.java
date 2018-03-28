package me.caosh.condition.infrastructure.tunnel.model;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.condorder.domain.orders.turnpoint.TurnPointCondition;
import me.caosh.autoasm.MappedClass;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/19.
 */
@MappedClass(TurnPointCondition.class)
public class TurnPointDynamicPropertiesDO implements DynamicPropertiesDO {
    private Boolean broken;
    private BigDecimal lowestPrice;

    public TurnPointDynamicPropertiesDO() {
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
                .add("broken", broken)
                .add("lowestPrice", lowestPrice)
                .toString();
    }
}
