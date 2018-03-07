package hbec.intellitrade.condorder.domain.orders;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.condorder.domain.StrategyState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import org.joda.time.LocalDateTime;

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

    public PriceOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                      PriceCondition priceCondition, LocalDateTime expireTime, BasicTradePlan tradePlan,
                      StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, expireTime, tradePlan, strategyState);
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
        return MoreObjects.toStringHelper(PriceOrder.class).omitNullValues()
                .add("orderId", getOrderId())
                .add("customer", getCustomer())
                .add("strategyState", getStrategyState())
                .add("securityInfo", getSecurityInfo())
                .add("rawCondition", getRawCondition())
                .add("expireTime", getExpireTime())
                .add("tradePlan", getTradePlan())
                .toString();
    }
}
