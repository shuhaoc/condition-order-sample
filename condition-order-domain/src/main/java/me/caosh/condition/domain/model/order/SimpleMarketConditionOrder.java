package me.caosh.condition.domain.model.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.StrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.EntrustResultAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class SimpleMarketConditionOrder extends MarketConditionOrder implements EntrustResultAware {
    private static final Logger logger = LoggerFactory.getLogger(SimpleMarketConditionOrder.class);

    private final SingleDirectionTradePlan tradePlan;

    public SimpleMarketConditionOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                                      StrategyInfo strategyInfo, SingleDirectionTradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, strategyInfo, orderState);
        this.tradePlan = tradePlan;
    }

    public abstract SimpleMarketCondition getMarketCondition();

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public TradeSignal onRealTimeMarketUpdate(RealTimeMarket realTimeMarket) {
        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        logger.debug("Check market condition, orderId={}, currentPrice={}, condition={}",
                getOrderId(), currentPrice, getCondition());
        if (getMarketCondition().isSatisfiedBy(currentPrice)) {
            return SignalFactory.getInstance().general();
        }
        return SignalFactory.getInstance().none();
    }

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        return OnceOrders.createEntrustCommand(this, realTimeMarket);
    }

    @Override
    public void afterEntrustReturned(TriggerContext triggerContext, EntrustResult entrustResult) {
        setOrderState(OrderState.TERMINATED);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(super.toString())
                .add("tradePlan", tradePlan)
                .toString();
    }
}
