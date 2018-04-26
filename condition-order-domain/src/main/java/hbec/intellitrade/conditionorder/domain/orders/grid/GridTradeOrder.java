package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.AbstractMarketConditionOrder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.OfferedPriceBidirectionalTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.conditionorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网格交易
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/23
 */
public class GridTradeOrder extends AbstractMarketConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(GridTradeOrder.class);

    private final DecoratedGridCondition gridCondition;
    private final OfferedPriceBidirectionalTradePlan tradePlan;

    public GridTradeOrder(Long orderId,
            TradeCustomerInfo tradeCustomerInfo,
            SecurityInfo securityInfo,
            DecoratedGridCondition gridCondition,
            LocalDateTime expireTime,
            OfferedPriceBidirectionalTradePlan tradePlan,
            OrderState orderState) {
        super(orderId,
                tradeCustomerInfo,
                orderState,
                securityInfo,
                expireTime,
                null,
                NoneMonitorTimeRange.NONE
        );
        this.gridCondition = gridCondition;
        this.tradePlan = tradePlan;
    }

    @Override
    public DecoratedGridCondition getCondition() {
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
        logger.info("Changing base price: {} => {}", gridCondition.getBasePrice(), realTimeMarket.getCurrentPrice());
        gridCondition.changeBasePrice(realTimeMarket.getCurrentPrice());
    }
}
