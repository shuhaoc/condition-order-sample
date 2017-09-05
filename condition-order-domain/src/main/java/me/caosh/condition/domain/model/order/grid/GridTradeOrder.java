package me.caosh.condition.domain.model.order.grid;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.EntrustCommands;
import me.caosh.condition.domain.model.order.MarketConditionOrder;
import me.caosh.condition.domain.model.order.TradeCustomerIdentity;
import me.caosh.condition.domain.model.order.TriggerContext;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caosh on 2017/8/23.
 */
public class GridTradeOrder extends MarketConditionOrder implements EntrustResultAware {
    private static final Logger logger = LoggerFactory.getLogger(GridTradeOrder.class);

    private final GridCondition gridCondition;
    private final DoubleDirectionTradePlan tradePlan;

    public GridTradeOrder(Long orderId, TradeCustomerIdentity customerIdentity, boolean deleted, SecurityInfo securityInfo,
                          GridCondition gridCondition, DoubleDirectionTradePlan tradePlan, OrderState orderState) {
        super(orderId, customerIdentity, deleted, securityInfo, NativeStrategyInfo.GRID, orderState);
        this.gridCondition = gridCondition;
        this.tradePlan = tradePlan;
    }

    @Override
    public Condition getCondition() {
        return getGridCondition();
    }

    public GridCondition getGridCondition() {
        return gridCondition;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public TradeSignal onRealTimeMarketUpdate(RealTimeMarket realTimeMarket) {
        logger.debug("Checking order {}", getOrderId());
        return gridCondition.onRealTimeMarketUpdate(realTimeMarket);
    }

    @Override
    public EntrustCommand onTradeSignal(TradeSignal signal, RealTimeMarket realTimeMarket) {
        if (signal instanceof Buy) {
            SingleDirectionTradePlan buyPlan = tradePlan.getBuyPlan();
            return EntrustCommands.createEntrustCommand(getCustomerIdentity(), getSecurityInfo(), buyPlan, realTimeMarket);
        } else if (signal instanceof Sell) {
            SingleDirectionTradePlan sellPlan = tradePlan.getSellPlan();
            return EntrustCommands.createEntrustCommand(getCustomerIdentity(), getSecurityInfo(), sellPlan, realTimeMarket);
        }
        throw new IllegalArgumentException(String.valueOf(signal));
    }

    @Override
    public void afterEntrustReturned(TriggerContext triggerContext, EntrustResult entrustResult) {
        Preconditions.checkNotNull(triggerContext.getTriggerRealTimeMarket().isPresent());
        RealTimeMarket triggerRealTimeMarket = triggerContext.getTriggerRealTimeMarket().get();
        logger.info("Changing base price {} => {}", gridCondition.getBasePrice(), triggerRealTimeMarket.getCurrentPrice());
        gridCondition.setBasePrice(triggerRealTimeMarket.getCurrentPrice());
    }
}
