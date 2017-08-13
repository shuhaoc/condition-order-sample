package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class ConditionOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Integer orderState;
    private SecurityInfoDTO securityInfo;
    private Integer strategyId;
    private ConditionDTO condition;
    private TradePlanDTO tradePlanDTO;

    public ConditionOrderDTO() {
    }

    public ConditionOrderDTO(Long orderId, Integer orderState, SecurityInfoDTO securityInfo, Integer strategyId,
                             ConditionDTO condition, TradePlanDTO tradePlanDTO) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.securityInfo = securityInfo;
        this.strategyId = strategyId;
        this.condition = condition;
        this.tradePlanDTO = tradePlanDTO;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public SecurityInfoDTO getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfoDTO securityInfo) {
        this.securityInfo = securityInfo;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public ConditionDTO getCondition() {
        return condition;
    }

    public void setCondition(ConditionDTO condition) {
        this.condition = condition;
    }

    public TradePlanDTO getTradePlanDTO() {
        return tradePlanDTO;
    }

    public void setTradePlanDTO(TradePlanDTO tradePlanDTO) {
        this.tradePlanDTO = tradePlanDTO;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .add("orderState", orderState)
                .add("securityInfo", securityInfo)
                .add("strategyId", strategyId)
                .add("condition", condition)
                .add("tradePlanDTO", tradePlanDTO)
                .toString();
    }
}
