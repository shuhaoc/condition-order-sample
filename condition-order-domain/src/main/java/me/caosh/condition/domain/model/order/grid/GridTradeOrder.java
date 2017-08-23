package me.caosh.condition.domain.model.order.grid;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.MarketConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.order.shared.DynamicProperty;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.StrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/23.
 */
public class GridTradeOrder extends MarketConditionOrder {
    private BigDecimal gridLength;
    private DynamicProperty<BigDecimal> basePrice;

    public GridTradeOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                          StrategyInfo strategyInfo, Condition condition, TradePlan tradePlan, OrderState orderState,
                          BigDecimal gridLength, DynamicProperty<BigDecimal> basePrice) {
        super(orderId, customerIdentity, deleted, securityInfo, strategyInfo, condition, tradePlan, orderState);
        this.gridLength = gridLength;
        this.basePrice = basePrice;
    }

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        return null;
    }
}
