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
import hbec.intellitrade.strategy.domain.timerange.MonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.NoneMonitorTimeRange;
import hbec.intellitrade.strategy.domain.timerange.WeekTimeRangeBuilder;
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
    private WeekTimeRangeBuilder monitorTimeRange = new WeekTimeRangeBuilder();

    public ConditionOrderBuilder setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public TradeCustomerInfoBuilder getCustomer() {
        return customer;
    }

    public ConditionOrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public SecurityInfoBuilder getSecurityInfo() {
        return securityInfo;
    }

    public StrategyInfoBuilder getStrategyInfo() {
        return strategyInfo;
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

    public DelayConfirmParamBuilder getDelayConfirmParam() {
        return delayConfirmParam;
    }

    public ConditionOrderBuilder setDelayConfirmCount(DelayConfirmCount delayConfirmCount) {
        this.delayConfirmCount = delayConfirmCount;
        return this;
    }

    public WeekTimeRangeBuilder getMonitorTimeRange() {
        return monitorTimeRange;
    }

    @Override
    public ConditionOrder build() {
        MonitorTimeRange weekTimeRange = buildMonitorTimeRange();

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
                weekTimeRange);
    }

    private MonitorTimeRange buildMonitorTimeRange() {
        MonitorTimeRange weekTimeRange;
        if (monitorTimeRange.isValid()) {
            weekTimeRange = monitorTimeRange.build();
        } else {
            weekTimeRange = NoneMonitorTimeRange.INSTANCE;
        }
        return weekTimeRange;
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