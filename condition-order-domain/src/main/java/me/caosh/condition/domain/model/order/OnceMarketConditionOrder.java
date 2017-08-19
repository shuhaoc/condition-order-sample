package me.caosh.condition.domain.model.order;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.turnpoint.TurnUpCondition;
import me.caosh.condition.domain.model.signal.SignalFactory;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.StrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustPriceSelector;
import me.caosh.condition.domain.model.trade.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/19.
 */
public class OnceMarketConditionOrder extends ConditionOrder implements RealTimeMarketDriven {
    private static final Logger logger = LoggerFactory.getLogger(OnceMarketConditionOrder.class);

    public OnceMarketConditionOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
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

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        BigDecimal entrustPrice = EntrustPriceSelector.selectPrice(realTimeMarket, getTradePlan().getEntrustStrategy());
        return new EntrustCommand(getCustomerIdentity(), getSecurityInfo(), getTradePlan().getExchangeType(),
                entrustPrice, getTradePlan().getTradeNumber().getNumber(entrustPrice), OrderType.LIMITED);
    }
}
