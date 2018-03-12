package hbec.intellitrade.condorder.domain.orders;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.condorder.domain.AbstractSimpleMarketConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.strategy.domain.MarketClosedEventListener;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirmParam;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import hbec.intellitrade.strategy.domain.strategies.condition.PriceCondition;
import org.joda.time.LocalDateTime;

/**
 * 价格条件单
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/1
 */
public class PriceOrder extends AbstractSimpleMarketConditionOrder implements MarketClosedEventListener {

    private final PriceCondition priceCondition;
    private final DelayConfirmParam delayConfirmParam;
    private final MarketCondition compositeCondition;

    public PriceOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, OrderState orderState, SecurityInfo securityInfo,
                      PriceCondition priceCondition, LocalDateTime expireTime, BasicTradePlan tradePlan) {
        this(orderId, tradeCustomerInfo, orderState, securityInfo, priceCondition, expireTime, tradePlan, DisabledDelayConfirmParam.INSTANCE);
    }

    public PriceOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, OrderState orderState, SecurityInfo securityInfo,
                      PriceCondition priceCondition, LocalDateTime expireTime, BasicTradePlan tradePlan, DelayConfirmParam delayConfirmParam) {
        super(orderId, tradeCustomerInfo, securityInfo, expireTime, tradePlan, orderState);
        this.priceCondition = priceCondition;
        this.delayConfirmParam = delayConfirmParam;
        this.compositeCondition = DelayConfirmConditionFactory.INSTANCE.wrapWith(priceCondition, delayConfirmParam);
    }

    @Override
    public MarketCondition getCondition() {
        return compositeCondition;
    }

    @Override
    public Condition getRawCondition() {
        return priceCondition;
    }

    public DelayConfirmParam getDelayConfirmParam() {
        return delayConfirmParam;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.PRICE;
    }

    @Override
    public void onMarketClosed(LocalDateTime localDateTime) {

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
                .add("strategyState", getOrderState())
                .add("securityInfo", getSecurityInfo())
                .add("condition", getCondition())
                .add("expireTime", getExpireTime())
                .add("tradePlan", getTradePlan())
                .toString();
    }
}
