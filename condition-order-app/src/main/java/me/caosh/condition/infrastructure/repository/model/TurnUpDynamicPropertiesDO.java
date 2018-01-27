package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.MappedClass;
import me.caosh.condition.domain.model.condition.TurnUpCondition;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/19.
 */
@MappedClass(TurnUpCondition.class)
public class TurnUpDynamicPropertiesDO implements DynamicPropertiesDO {
    private Boolean broken;
    private BigDecimal lowestPrice;

    public TurnUpDynamicPropertiesDO() {
    }

    public TurnUpDynamicPropertiesDO(Boolean broken, BigDecimal lowestPrice) {
        this.broken = broken;
        this.lowestPrice = lowestPrice;
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

    @Override
    public void accept(DynamicPropertiesDOVisitor visitor) {
        visitor.visitTurnUpDynamicPropertiesDO(this);
    }
}
