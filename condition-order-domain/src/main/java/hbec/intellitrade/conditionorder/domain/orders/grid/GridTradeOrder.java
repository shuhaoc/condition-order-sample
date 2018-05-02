package hbec.intellitrade.conditionorder.domain.orders.grid;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.common.market.RealTimeMarket;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.conditionorder.domain.AbstractMarketConditionOrder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.conditionorder.domain.tradeplan.BaseBidirectionalTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradePlan;
import hbec.intellitrade.conditionorder.domain.trigger.TriggerTradingContext;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 网格交易
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/23
 */
public class GridTradeOrder extends AbstractMarketConditionOrder {
    private static final Logger logger = LoggerFactory.getLogger(GridTradeOrder.class);

    private final DecoratedGridCondition gridCondition;
    private final BaseBidirectionalTradePlan tradePlan;

    public GridTradeOrder(Long orderId,
            TradeCustomerInfo tradeCustomerInfo,
            OrderState orderState,
            SecurityInfo securityInfo,
            DecoratedGridCondition gridCondition,
            LocalDateTime expireTime,
            BaseBidirectionalTradePlan tradePlan,
            MonitorTimeRange monitorTimeRange) {
        super(orderId,
                tradeCustomerInfo,
                orderState,
                securityInfo,
                expireTime,
                null,
                monitorTimeRange
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

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        GridTradeOrder that = (GridTradeOrder) o;
        return Objects.equals(gridCondition, that.gridCondition) &&
                Objects.equals(tradePlan, that.tradePlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gridCondition, tradePlan);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(GridTradeOrder.class).omitNullValues()
                .add("orderId", getOrderId())
                .add("customer", getCustomer())
                .add("orderState", getOrderState())
                .add("securityInfo", getSecurityInfo())
                .add("condition", getCondition())
                .add("expireTime", getExpireTime())
                .add("tradePlan", getTradePlan())
                .add("monitorTimeRange", getMonitorTimeRange())
                .toString();
    }
}
