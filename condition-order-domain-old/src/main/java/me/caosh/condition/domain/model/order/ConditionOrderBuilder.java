package me.caosh.condition.domain.model.order;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.condorder.domain.OrderState;
import hbec.intellitrade.condorder.domain.TradeCustomerInfoBuilder;
import hbec.intellitrade.condorder.domain.delayconfirm.count.DelayConfirmCount;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanBuilder;
import hbec.intellitrade.strategy.domain.condition.Condition;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmParamBuilder;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlParamBuilder;
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
    private StrategyInfoBuilder strategyInfo = new StrategyInfoBuilder();
    private Condition rawCondition;
    private LocalDateTime expireTime;
    private TradePlanBuilder tradePlan = new TradePlanBuilder();
    private DelayConfirmParamBuilder delayConfirmParam = new DelayConfirmParamBuilder();
    private DelayConfirmCount delayConfirmCount;
    private MonitorTimeRangeBuilder monitorTimeRange = new MonitorTimeRangeBuilder();
    private DeviationCtrlParamBuilder deviationCtrlParam = new DeviationCtrlParamBuilder();

    public ConditionOrderBuilder setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public TradeCustomerInfoBuilder getCustomer() {
        return customer;
    }

    public void setCustomer(TradeCustomerInfoBuilder customer) {
        this.customer = customer;
    }

    public ConditionOrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public SecurityInfoBuilder getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfoBuilder securityInfo) {
        this.securityInfo = securityInfo;
    }

    public StrategyInfoBuilder getStrategyInfo() {
        return strategyInfo;
    }

    public void setStrategyInfo(StrategyInfoBuilder strategyInfo) {
        this.strategyInfo = strategyInfo;
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

    public DelayConfirmParamBuilder getDelayConfirmParam() {
        return delayConfirmParam;
    }

    public void setDelayConfirmParam(DelayConfirmParamBuilder delayConfirmParam) {
        this.delayConfirmParam = delayConfirmParam;
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

    public DeviationCtrlParamBuilder getDeviationCtrlParam() {
        return deviationCtrlParam;
    }

    public void setDeviationCtrlParam(DeviationCtrlParamBuilder deviationCtrlParam) {
        this.deviationCtrlParam = deviationCtrlParam;
    }

    @Override
    public ConditionOrder build() {
        return ConditionOrderFactory.getInstance().create(
                orderId,
                customer.build(),
                orderState,
                securityInfo.build(),
                strategyInfo.getStrategyType(),
                rawCondition,
                expireTime,
                tradePlan.build(),
                delayConfirmParam.build(),
                delayConfirmCount,
                monitorTimeRange.build(),
                deviationCtrlParam.build());
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