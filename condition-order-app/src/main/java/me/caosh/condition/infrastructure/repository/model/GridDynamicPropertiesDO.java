package me.caosh.condition.infrastructure.repository.model;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.MappedClass;
import me.caosh.condition.domain.model.order.grid.GridCondition;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/19.
 */
@MappedClass(GridCondition.class)
public class GridDynamicPropertiesDO implements DynamicPropertiesDO {
    private BigDecimal basePrice;

    public GridDynamicPropertiesDO() {
    }

    public GridDynamicPropertiesDO(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("basePrice", basePrice)
                .toString();
    }

    @Override
    public void accept(DynamicPropertiesDOVisitor visitor) {
        visitor.visitGridDynamicPropertiesDO(this);
    }
}
