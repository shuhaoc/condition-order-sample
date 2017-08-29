package me.caosh.condition.domain.model.order.grid;

import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.order.constant.OrderState;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.SingleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.Buy;
import me.caosh.condition.domain.model.signal.Sell;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.NativeStrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.EntrustResultAware;

/**
 * Created by caosh on 2017/8/23.
 */
public class GridTradeOrder extends MarketConditionOrder implements EntrustResultAware {

    private final GridCondition gridCondition;
    private final DoubleDirectionTradePlan tradePlan;

    public GridTradeOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                          GridCondition gridCondition, DoubleDirectionTradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, NativeStrategyInfo.GRID, orderState);
        this.gridCondition = gridCondition;
        this.tradePlan = tradePlan;
    }

    public DoubleDirectionTradePlan getDoubleDirectionTradePlan() {
        return tradePlan;
    }

    @Override
    public Condition getCondition() {
        return gridCondition;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public TradeSignal onRealTimeMarketUpdate(RealTimeMarket realTimeMarket) {
        return gridCondition.onRealTimeMarketUpdate(realTimeMarket);
    }

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        if (signal instanceof Buy) {
            SingleDirectionTradePlan buyPlan = getDoubleDirectionTradePlan().getBuyPlan();
            return EntrustCommands.createEntrustCommand(getCustomerIdentity(), getSecurityInfo(), buyPlan, realTimeMarket);
        } else if (signal instanceof Sell) {
            SingleDirectionTradePlan sellPlan = getDoubleDirectionTradePlan().getSellPlan();
            return EntrustCommands.createEntrustCommand(getCustomerIdentity(), getSecurityInfo(), sellPlan, realTimeMarket);
        }
        throw new IllegalArgumentException(String.valueOf(signal));
    }

    @Override
    public void afterEntrustReturned(TriggerContext triggerContext, EntrustResult entrustResult) {
        RealTimeMarket triggerRealTimeMarket = triggerContext.getTriggerRealTimeMarket();
        gridCondition.setBasePrice(triggerRealTimeMarket.getCurrentPrice());
    }
}
