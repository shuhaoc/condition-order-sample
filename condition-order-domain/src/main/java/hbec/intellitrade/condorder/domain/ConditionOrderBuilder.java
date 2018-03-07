package hbec.intellitrade.condorder.domain;

import hbec.intellitrade.common.security.SecurityInfoBuilder;
import hbec.intellitrade.condorder.domain.strategyinfo.NativeStrategyInfo;
import hbec.intellitrade.condorder.domain.tradeplan.TradePlanBuilder;
import hbec.intellitrade.strategy.domain.condition.Condition;
import me.caosh.autoasm.ConvertibleBuilder;
import org.joda.time.LocalDateTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class ConditionOrderBuilder implements ConvertibleBuilder<ConditionOrder> {
    private Long orderId;
    private TradeCustomerInfoBuilder customer = new TradeCustomerInfoBuilder();
    private SecurityInfoBuilder securityInfo = new SecurityInfoBuilder();
    private StrategyInfoBuilder strategyInfo = new StrategyInfoBuilder();
    private Condition rawCondition;
    private LocalDateTime expireTime;
    private TradePlanBuilder tradePlan = new TradePlanBuilder();
    private OrderState orderState;

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

    public SecurityInfoBuilder getSecurityInfo() {
        return securityInfo;
    }

    public ConditionOrderBuilder setSecurityInfo(SecurityInfoBuilder securityInfo) {
        this.securityInfo = securityInfo;
        return this;
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

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public TradePlanBuilder getTradePlan() {
        return tradePlan;
    }

    public ConditionOrderBuilder setTradePlan(TradePlanBuilder tradePlan) {
        this.tradePlan = tradePlan;
        return this;
    }

    public ConditionOrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
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
                tradePlan.build());
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