package me.caosh.condition.domain.dto.order;

import me.caosh.condition.domain.dto.market.SecurityInfoDTO;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public final class ConditionOrderDTOBuilder {
    private Long orderId;
    private Integer orderState;
    private SecurityInfoDTO securityInfo;
    private Integer strategyId;
    private ConditionDTO condition;
    private TradePlanDTO tradePlanDTO;

    private ConditionOrderDTOBuilder() {
    }

    public static ConditionOrderDTOBuilder aConditionOrderDTO() {
        return new ConditionOrderDTOBuilder();
    }

    public ConditionOrderDTOBuilder withOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public ConditionOrderDTOBuilder withOrderState(Integer orderState) {
        this.orderState = orderState;
        return this;
    }

    public ConditionOrderDTOBuilder withSecurityInfo(SecurityInfoDTO securityInfo) {
        this.securityInfo = securityInfo;
        return this;
    }

    public ConditionOrderDTOBuilder withStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
        return this;
    }

    public ConditionOrderDTOBuilder withCondition(ConditionDTO condition) {
        this.condition = condition;
        return this;
    }

    public ConditionOrderDTOBuilder withTradePlanDTO(TradePlanDTO tradePlanDTO) {
        this.tradePlanDTO = tradePlanDTO;
        return this;
    }

    public ConditionOrderDTO build() {
        ConditionOrderDTO conditionOrderDTO = new ConditionOrderDTO();
        conditionOrderDTO.setOrderId(orderId);
        conditionOrderDTO.setOrderState(orderState);
        conditionOrderDTO.setSecurityInfo(securityInfo);
        conditionOrderDTO.setStrategyId(strategyId);
        conditionOrderDTO.setCondition(condition);
        conditionOrderDTO.setTradePlanDTO(tradePlanDTO);
        return conditionOrderDTO;
    }
}
