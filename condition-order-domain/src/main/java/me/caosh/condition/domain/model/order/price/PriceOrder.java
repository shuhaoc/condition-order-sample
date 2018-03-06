package me.caosh.condition.domain.model.order.price;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.condition.PriceCondition;
import me.caosh.condition.domain.model.order.AbstractSimpleMarketConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;

/**
 * 价格条件单
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/1
 */
public class PriceOrder extends AbstractSimpleMarketConditionOrder {

    private final PriceCondition priceCondition;

    public PriceOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                      PriceCondition priceCondition, BasicTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, tradePlan, strategyState);
        this.priceCondition = priceCondition;
    }

    @Override
    public MarketCondition getCondition() {
        return priceCondition;
    }

    @Override
    public Condition getRawCondition() {
        return priceCondition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.PRICE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        PriceOrder that = (PriceOrder) o;

        return priceCondition.equals(that.priceCondition);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + priceCondition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("priceCondition", priceCondition)
                .toString();
    }
}
