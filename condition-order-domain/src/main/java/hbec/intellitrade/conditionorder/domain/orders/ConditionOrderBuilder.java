package hbec.intellitrade.conditionorder.domain.orders;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.conditionorder.domain.ConditionOrder;
import hbec.intellitrade.conditionorder.domain.OrderState;
import hbec.intellitrade.conditionorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.conditionorder.domain.orders.price.DecoratedPriceCondition;
import hbec.intellitrade.conditionorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeOrder;
import hbec.intellitrade.conditionorder.domain.orders.time.TimeReachedCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.DecoratedTurnPointCondition;
import hbec.intellitrade.conditionorder.domain.orders.turnpoint.TurnPointOrder;
import hbec.intellitrade.conditionorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.conditionorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.conditionorder.domain.trackindex.TrackedIndexInfoBuilder;
import hbec.intellitrade.conditionorder.domain.tradeplan.BaseTradePlan;
import hbec.intellitrade.conditionorder.domain.tradeplan.TradePlanBuilder;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRangeBuilder;
import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class ConditionOrderBuilder implements ConvertibleBuilder<ConditionOrder> {
    private Long orderId;
    private TradeCustomerInfoBuilder customer = new TradeCustomerInfoBuilder();
    private OrderState orderState;
    private SecurityInfoBuilder securityInfo = new SecurityInfoBuilder();
    private TrackedIndexInfoBuilder trackedIndex = new TrackedIndexInfoBuilder();
    private StrategyInfoBuilder strategyInfo = new StrategyInfoBuilder();
    private ConvertibleBuilder<? extends Condition> condition;
    private LocalDateTime expireTime;
    private TradePlanBuilder tradePlan = new TradePlanBuilder();
    private MonitorTimeRangeBuilder monitorTimeRange = new MonitorTimeRangeBuilder();

    public ConditionOrderBuilder() {
    }

    @SuppressWarnings("unchecked")
    public ConditionOrderBuilder(Class<? extends ConvertibleBuilder> conditionBuilderClass) {
        try {
            this.condition = conditionBuilderClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public ConditionOrderBuilder setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public TradeCustomerInfoBuilder getCustomer() {
        return customer;
    }

    public ConditionOrderBuilder setCustomer(TradeCustomerInfoBuilder customer) {
        this.customer = customer;
        return this;
    }

    public ConditionOrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public SecurityInfoBuilder getSecurityInfo() {
        return securityInfo;
    }

    public ConditionOrderBuilder setSecurityInfo(SecurityInfoBuilder securityInfo) {
        this.securityInfo = securityInfo;
        return this;
    }

    public TrackedIndexInfoBuilder getTrackedIndex() {
        return trackedIndex;
    }

    public void setTrackedIndex(TrackedIndexInfoBuilder trackedIndex) {
        this.trackedIndex = trackedIndex;
    }

    public StrategyInfoBuilder getStrategyInfo() {
        return strategyInfo;
    }

    public ConditionOrderBuilder setStrategyInfo(StrategyInfoBuilder strategyInfo) {
        this.strategyInfo = strategyInfo;
        return this;
    }

    public ConvertibleBuilder<? extends Condition> getCondition() {
        return condition;
    }

    public ConditionOrderBuilder setCondition(ConvertibleBuilder<? extends Condition> condition) {
        this.condition = condition;
        return this;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public TradePlanBuilder getTradePlan() {
        return tradePlan;
    }

    public void setTradePlan(TradePlanBuilder tradePlan) {
        this.tradePlan = tradePlan;
    }

    public MonitorTimeRangeBuilder getMonitorTimeRange() {
        return monitorTimeRange;
    }

    public void setMonitorTimeRange(MonitorTimeRangeBuilder monitorTimeRange) {
        this.monitorTimeRange = monitorTimeRange;
    }

    public static class StrategyInfoBuilder {
        private NativeStrategyInfo strategyType;

        public NativeStrategyInfo getStrategyType() {
            return strategyType;
        }

        public void setStrategyType(NativeStrategyInfo strategyType) {
            this.strategyType = strategyType;
        }
    }

    @Override
    public ConditionOrder build() {
        StrategyInfo strategyInfo = this.strategyInfo.getStrategyType();
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceOrder(orderId,
                                  customer.build(),
                                  orderState,
                                  securityInfo.build(),
                                  (DecoratedPriceCondition) condition.build(),
                                  expireTime,
                                  (BaseTradePlan) tradePlan.build(),
                                  trackedIndex.build(),
                                  monitorTimeRange.build());
        } else if (strategyInfo == NativeStrategyInfo.TURN_POINT) {
            return new TurnPointOrder(orderId,
                                      customer.build(),
                                      orderState,
                                      securityInfo.build(),
                                      (DecoratedTurnPointCondition) condition.build(),
                                      expireTime,
                                      (BaseTradePlan) tradePlan.build(),
                                      trackedIndex.build(),
                                      monitorTimeRange.build());
        } else if (strategyInfo == NativeStrategyInfo.TIME) {
            return new TimeOrder(orderId,
                    customer.build(),
                    orderState,
                    securityInfo.build(),
                    (TimeReachedCondition) condition.build(),
                    expireTime,
                    (BaseTradePlan) tradePlan.build());
//        } else if (strategyInfo == NativeStrategyInfo.GRID) {
//            return new GridTradeOrder(orderId, tradeCustomerInfo, securityInfo, (GridCondition) condition,
//                                      null, (DoubleDirectionTradePlan) tradePlan, orderState);
//        } else if (strategyInfo == NativeStrategyInfo.NEW_STOCK) {
//            return new NewStockOrder(orderId,
//                                     tradeCustomerInfo,
//                                     (NewStockPurchaseCondition) condition,
//                                     expireTime,
//                                     orderState);
        }
        throw new IllegalArgumentException("strategyInfo=" + strategyInfo);
    }
}