package hbec.commons.domain.intellitrade.condition;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.conditionorder.domain.orders.price.DecoratedPriceCondition;
import hbec.intellitrade.conditionorder.domain.orders.price.DecoratedPriceConditionBuilder;
import me.caosh.autoasm.MappedClass;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@MappedClass(value = DecoratedPriceCondition.class, builderClass = DecoratedPriceConditionBuilder.class)
public class PriceConditionDTO implements ConditionDTO {
    private static final long serialVersionUID = 1L;

    private Integer compareOperator;
    private BigDecimal targetPrice;
    private Integer delayConfirmedCount;

    public PriceConditionDTO() {
    }

    public PriceConditionDTO(Integer compareOperator, BigDecimal targetPrice, Integer delayConfirmedCount) {
        this.compareOperator = compareOperator;
        this.targetPrice = targetPrice;
        this.delayConfirmedCount = delayConfirmedCount;
    }

    public Integer getCompareOperator() {
        return compareOperator;
    }

    public void setCompareOperator(Integer compareOperator) {
        this.compareOperator = compareOperator;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Integer getDelayConfirmedCount() {
        return delayConfirmedCount;
    }

    public void setDelayConfirmedCount(Integer delayConfirmedCount) {
        this.delayConfirmedCount = delayConfirmedCount;
    }

    public BigDecimal getTargetValue() {
        return getTargetPrice();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceConditionDTO.class).omitNullValues()
                          .add("compareOperator", compareOperator)
                          .add("targetPrice", targetPrice)
                          .add("delayConfirmedCount", delayConfirmedCount)
                          .toString();
    }
}
