package me.caosh.condition.domain.model.order;

import me.caosh.autoasm.ConvertibleBuilder;
import me.caosh.condition.domain.model.market.SecurityInfoBuilder;
import me.caosh.condition.domain.model.order.constant.StrategyState;
import me.caosh.condition.domain.model.order.plan.TradePlanBuilder;
import me.caosh.condition.domain.model.strategy.condition.Condition;
import me.caosh.condition.domain.model.strategyinfo.NativeStrategyInfo;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/4
 */
public class ConditionOrderBuilder implements ConvertibleBuilder<ConditionOrder> {
    private Long orderId;
    private TradeCustomerBuilder customer = new TradeCustomerBuilder();
    private SecurityInfoBuilder securityInfo = new SecurityInfoBuilder();
    private StrategyInfoBuilder strategyInfo = new StrategyInfoBuilder();
    private Condition rawCondition;
    private TradePlanBuilder tradePlan = new TradePlanBuilder();
    private StrategyState strategyState;

    public ConditionOrderBuilder setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public TradeCustomerBuilder getCustomer() {
        return customer;
    }

    public ConditionOrderBuilder setCustomer(TradeCustomerBuilder customer) {
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

    public TradePlanBuilder getTradePlan() {
        return tradePlan;
    }

    public ConditionOrderBuilder setTradePlan(TradePlanBuilder tradePlan) {
        this.tradePlan = tradePlan;
        return this;
    }

    public ConditionOrderBuilder setStrategyState(StrategyState strategyState) {
        this.strategyState = strategyState;
        return this;
    }

    @Override
    public ConditionOrder build() {
        return ConditionOrderFactory.getInstance().create(
                orderId,
                customer.build(),
                strategyState,
                securityInfo.build(),
                strategyInfo.getStrategyTemplateId(),
                rawCondition,
                tradePlan.build());
    }

    public static class StrategyInfoBuilder {
        private NativeStrategyInfo strategyTemplateId;

        public NativeStrategyInfo getStrategyTemplateId() {
            return strategyTemplateId;
        }

        public void setStrategyTemplateId(NativeStrategyInfo strategyTemplateId) {
            this.strategyTemplateId = strategyTemplateId;
        }
    }
}