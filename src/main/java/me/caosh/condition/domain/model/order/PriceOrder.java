package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;

/**
 * Created by caosh on 2017/8/1.
 */
public class PriceOrder implements ConditionOrder {
    private final Integer orderId;
    private final SecurityInfo securityInfo;
    private final SimplePriceCondition simplePriceCondition;
    private final TradePlan tradePlan;

    public PriceOrder(Integer orderId, SecurityInfo securityInfo, TradePlan tradePlan, SimplePriceCondition simplePriceCondition) {
        this.orderId = orderId;
        this.securityInfo = securityInfo;
        this.tradePlan = tradePlan;
        this.simplePriceCondition = simplePriceCondition;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public SimplePriceCondition getSimplePriceCondition() {
        return simplePriceCondition;
    }

    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("securityInfo", securityInfo)
                .add("entrustParameter", tradePlan)
                .add("simplePriceCondition", simplePriceCondition)
                .toString();
    }
}
