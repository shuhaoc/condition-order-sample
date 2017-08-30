package me.caosh.condition.domain.model.order.price;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.MarketCondition;
import me.caosh.condition.domain.model.order.RealTimeMarketDriven;
import me.caosh.condition.domain.model.order.SimpleMarketConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;

/**
 * Created by caosh on 2017/8/1.
 */
public class PriceOrder extends SimpleMarketConditionOrder implements RealTimeMarketDriven {

    private final PriceCondition priceCondition;

    public PriceOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                      PriceCondition priceCondition, SingleDirectionTradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, NativeStrategyInfo.PRICE, tradePlan, orderState);
        this.priceCondition = priceCondition;
    }

    @Override
    public Condition getCondition() {
        return getMarketCondition();
    }

    @Override
    public MarketCondition getMarketCondition() {
        return priceCondition;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("priceCondition", priceCondition)
                .toString();
    }
}
