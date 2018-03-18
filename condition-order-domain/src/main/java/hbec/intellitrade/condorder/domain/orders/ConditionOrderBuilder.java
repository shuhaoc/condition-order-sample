package hbec.intellitrade.condorder.domain.orders;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.condorder.domain.delayconfirm.count.DelayConfirmCount;
import hbec.intellitrade.condorder.domain.delayconfirm.count.SingleDelayConfirmCount;
import hbec.intellitrade.condorder.domain.orders.price.PriceCondition;
import hbec.intellitrade.condorder.domain.orders.price.PriceOrder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.strategyinfo.StrategyInfo;
import hbec.intellitrade.condorder.domain.trackindex.TrackedIndexInfoBuilder;
import hbec.intellitrade.condorder.domain.tradeplan.BasicTradePlan;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanBuilder;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmBuilder;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlBuilder;
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
    private Condition rawCondition;
    private LocalDateTime expireTime;
    private TradePlanBuilder tradePlan = new TradePlanBuilder();
    private DelayConfirmBuilder delayConfirm = new DelayConfirmBuilder();
    private DelayConfirmCount delayConfirmCount;
    private MonitorTimeRangeBuilder monitorTimeRange = new MonitorTimeRangeBuilder();
    private DeviationCtrlBuilder deviationCtrl = new DeviationCtrlBuilder();

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

    public ConditionOrderBuilder setRawCondition(Condition rawCondition) {
        this.rawCondition = rawCondition;
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

    public DelayConfirmBuilder getDelayConfirm() {
        return delayConfirm;
    }

    public void setDelayConfirm(DelayConfirmBuilder delayConfirm) {
        this.delayConfirm = delayConfirm;
    }

    public ConditionOrderBuilder setDelayConfirmCount(DelayConfirmCount delayConfirmCount) {
        this.delayConfirmCount = delayConfirmCount;
        return this;
    }

    public MonitorTimeRangeBuilder getMonitorTimeRange() {
        return monitorTimeRange;
    }

    public void setMonitorTimeRange(MonitorTimeRangeBuilder monitorTimeRange) {
        this.monitorTimeRange = monitorTimeRange;
    }

    public DeviationCtrlBuilder getDeviationCtrl() {
        return deviationCtrl;
    }

    public void setDeviationCtrl(DeviationCtrlBuilder deviationCtrl) {
        this.deviationCtrl = deviationCtrl;
    }

    @Override
    public ConditionOrder build() {
        StrategyInfo strategyInfo = this.strategyInfo.getStrategyType();
        if (strategyInfo == NativeStrategyInfo.PRICE) {
            return new PriceOrder(orderId,
                                  customer.build(),
                                  orderState,
                                  securityInfo.build(),
                                  (PriceCondition) rawCondition,
                                  expireTime,
                                  (BasicTradePlan) tradePlan.build(),
                                  trackedIndex.build(),
                                  monitorTimeRange.build(),
                                  delayConfirm.build(),
                                  (SingleDelayConfirmCount) delayConfirmCount,
                                  deviationCtrl.build());
//        } else if (strategyInfo == NativeStrategyInfo.TURN_POINT) {
//            return new TurnUpBuyOrder(orderId, tradeCustomerInfo, securityInfo, (TurnUpCondition) condition,
//                                      null, (BasicTradePlan) tradePlan, orderState);
//        } else if (strategyInfo == NativeStrategyInfo.TIME) {
//            return new TimeOrder(orderId, tradeCustomerInfo, securityInfo, (TimeReachedCondition) condition, expireTime,
//                                 (BasicTradePlan) tradePlan, orderState);
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

    public static class StrategyInfoBuilder {
        private NativeStrategyInfo strategyType;

        public NativeStrategyInfo getStrategyType() {
            return strategyType;
        }

        public StrategyInfoBuilder setStrategyType(NativeStrategyInfo strategyType) {
            this.strategyType = strategyType;
            return this;
        }
    }
}