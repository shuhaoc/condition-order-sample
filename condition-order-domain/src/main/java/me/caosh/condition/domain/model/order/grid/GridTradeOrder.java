package me.caosh.condition.domain.model.order.grid;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import me.caosh.condition.domain.model.order.AbstractMarketConditionOrder;
import me.caosh.condition.domain.model.order.TriggerTradingContext;
import me.caosh.condition.domain.model.order.TradeCustomerInfo;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.market.MarketCondition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caosh on 2017/8/23.
 */
public class GridTradeOrder extends AbstractMarketConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(GridTradeOrder.class);

    private final GridCondition gridCondition;
    private final DoubleDirectionTradePlan tradePlan;

    public GridTradeOrder(Long orderId, TradeCustomerInfo tradeCustomerInfo, SecurityInfo securityInfo,
                          GridCondition gridCondition, DoubleDirectionTradePlan tradePlan, StrategyState strategyState) {
        super(orderId, tradeCustomerInfo, securityInfo, strategyState);
        this.gridCondition = gridCondition;
        this.tradePlan = tradePlan;
    }

    @Override
    public MarketCondition getCondition() {
        return getGridCondition();
    }

    @Override
    public Condition getRawCondition() {
        return gridCondition;
    }

    public GridCondition getGridCondition() {
        return gridCondition;
    }

    @Override
    public StrategyInfo getStrategyInfo() {
        return NativeStrategyInfo.GRID;
    }

    @Override
    public TradePlan getTradePlan() {
        return tradePlan;
    }

    @Override
    public void afterEntrustCommandsExecuted(TriggerTradingContext triggerTradingContext) {
        RealTimeMarket realTimeMarket = triggerTradingContext.getTriggerMarket();
        logger.info("Changing base price {} => {}", gridCondition.getBasePrice(), realTimeMarket.getCurrentPrice());
        gridCondition.setBasePrice(realTimeMarket.getCurrentPrice());
    }
}
