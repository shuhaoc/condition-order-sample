package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;

import java.time.LocalDateTime;

/**
 * Created by caosh on 2017/8/1.
 */
public class PriceOrder extends ConditionOrder {
    public PriceOrder(Long orderId, OrderState orderState, SecurityInfo securityInfo, SimplePriceCondition simplePriceCondition,
                      TradePlan tradePlan, LocalDateTime createTime, LocalDateTime updateTime) {
        super(orderId, orderState, securityInfo, simplePriceCondition, tradePlan, createTime, updateTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .toString();
    }
}
