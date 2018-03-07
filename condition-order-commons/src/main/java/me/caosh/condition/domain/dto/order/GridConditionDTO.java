package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/26.
 */
public class GridConditionDTO implements ConditionDTO {

    private BigDecimal gridLength;
    private BigDecimal basePrice;

    public GridConditionDTO() {
    }

    public GridConditionDTO(BigDecimal gridLength, BigDecimal basePrice) {
        this.gridLength = gridLength;
        this.basePrice = basePrice;
    }

    public BigDecimal getGridLength() {
        return gridLength;
    }

    public void setGridLength(BigDecimal gridLength) {
        this.gridLength = gridLength;
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
                .add("gridLength", gridLength)
                .add("basePrice", basePrice)
                .toString();
    }
}
