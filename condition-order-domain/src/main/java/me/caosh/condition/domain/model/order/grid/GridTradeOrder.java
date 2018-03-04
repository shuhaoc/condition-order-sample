package me.caosh.condition.domain.model.order.grid;

import com.google.common.base.Preconditions;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.market.SecurityInfo;
import me.caosh.condition.domain.model.order.*;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.DoubleDirectionTradePlan;
import me.caosh.condition.domain.model.order.plan.TradePlan;
import me.caosh.condition.domain.model.signal.TradeSignal;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategy.condition.market.MarketCondition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;
import me.caosh.condition.domain.model.strategyinfo.StrategyInfo;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.model.trade.EntrustResultAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caosh on 2017/8/23.
 */
public class GridTradeOrder extends AbstractMarketConditionOrder implements EntrustResultAware {
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
    public void onTradeSignal(TradeSignal tradeSignal, TradeCustomer tradeCustomer, TradingMarketSupplier tradingMarketSupplier, RealTimeMarket realTimeMarket) {
        super.onTradeSignal(tradeSignal, tradeCustomer, tradingMarketSupplier, realTimeMarket);

        logger.info("Changing base price {} => {}", gridCondition.getBasePrice(), realTimeMarket.getCurrentPrice());
        gridCondition.setBasePrice(realTimeMarket.getCurrentPrice());
    }

    @Override
    public void afterEntrustReturned(TriggerContext triggerContext, EntrustResult entrustResult) {
        Preconditions.checkArgument(triggerContext.getTriggerMarket().isPresent());
        RealTimeMarket triggerRealTimeMarket = triggerContext.getTriggerMarket().get();
        logger.info("Changing base price {} => {}", gridCondition.getBasePrice(), triggerRealTimeMarket.getCurrentPrice());
        gridCondition.setBasePrice(triggerRealTimeMarket.getCurrentPrice());
    }
}
