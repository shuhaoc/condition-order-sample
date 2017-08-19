package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.constant.CompareCondition;
import me.caosh.condition.domain.model.order.price.PriceCondition;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class PriceConditionDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    private Integer compareCondition;
    private BigDecimal targetPrice;

    public PriceConditionDTO() {
    }

    public PriceConditionDTO(Integer compareCondition, BigDecimal targetPrice) {
        this.compareCondition = compareCondition;
        this.targetPrice = targetPrice;
    }

    public Integer getCompareCondition() {
        return compareCondition;
    }

    public void setCompareCondition(Integer compareCondition) {
        this.compareCondition = compareCondition;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    @Override
    public void accept(ConditionDTOVisitor visitor) {
        visitor.visitPriceConditionDTO(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceConditionDTO.class).omitNullValues()
                .addValue(PriceConditionDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("compareCondition", compareCondition)
                .add("targetPrice", targetPrice)
                .toString();
    }
}
