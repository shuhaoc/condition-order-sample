package me.caosh.condition.infrastructure.tunnel.model;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/15.
 */
public class GridConditionDO implements ConditionDO {
    private BigDecimal gridLength;

    public GridConditionDO() {
    }

    public GridConditionDO(BigDecimal gridLength) {
        this.gridLength = gridLength;
    }

    public BigDecimal getGridLength() {
        return gridLength;
    }

    public void setGridLength(BigDecimal gridLength) {
        this.gridLength = gridLength;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gridLength", gridLength)
                .toString();
    }

}
