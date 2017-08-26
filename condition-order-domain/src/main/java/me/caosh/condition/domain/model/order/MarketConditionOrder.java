package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.StrategyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/20.
 */
public abstract class MarketConditionOrder extends ConditionOrder implements RealTimeMarketDriven {
    // TODO: 应重命名为单一条件的行情类条件单
    private static final Logger logger = LoggerFactory.getLogger(MarketConditionOrder.class);

    public MarketConditionOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                                StrategyInfo strategyInfo, Condition condition, TradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, strategyInfo, condition, tradePlan, orderState);
    }

    @Override
    public TradeSignal onRealTimeMarketUpdate(RealTimeMarket realTimeMarket) {
        BigDecimal currentPrice = realTimeMarket.getCurrentPrice();
        logger.debug("Check market condition, orderId={}, currentPrice={}, condition={}",
                getOrderId(), currentPrice, getCondition());
        if (((MarketCondition) getCondition()).isSatisfiedBy(currentPrice)) {
            return SignalFactory.getInstance().general();
        }
        return SignalFactory.getInstance().none();
    }
}
