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
    private Integer userId;
    private String customerNo;
    private Integer orderState;
    private SecurityInfoDTO securityInfoDTO;
    private Integer strategyId;
    private ConditionDTO conditionDTO;
    private TradePlanDTO tradePlanDTO;

    public ConditionOrderDTO() {
    }

    public ConditionOrderDTO(Long orderId, Integer userId, String customerNo, Integer orderState, SecurityInfoDTO securityInfoDTO,
                             Integer strategyId, ConditionDTO conditionDTO, TradePlanDTO tradePlanDTO) {
        this.orderId = orderId;
        this.userId = userId;
        this.customerNo = customerNo;
        this.orderState = orderState;
        this.securityInfoDTO = securityInfoDTO;
        this.strategyId = strategyId;
        this.conditionDTO = conditionDTO;
        this.tradePlanDTO = tradePlanDTO;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public SecurityInfoDTO getSecurityInfoDTO() {
        return securityInfoDTO;
    }

    public void setSecurityInfoDTO(SecurityInfoDTO securityInfoDTO) {
        this.securityInfoDTO = securityInfoDTO;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public ConditionDTO getConditionDTO() {
        return conditionDTO;
    }

    public void setConditionDTO(ConditionDTO conditionDTO) {
        this.conditionDTO = conditionDTO;
    }

    public TradePlanDTO getTradePlanDTO() {
        return tradePlanDTO;
    }

    public void setTradePlanDTO(TradePlanDTO tradePlanDTO) {
        this.tradePlanDTO = tradePlanDTO;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ConditionOrderDTO.class).omitNullValues()
                .addValue(ConditionOrderDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("orderId", orderId)
                .add("userId", userId)
                .add("customerNo", customerNo)
                .add("orderState", orderState)
                .add("securityInfo", securityInfoDTO)
                .add("strategyId", strategyId)
                .add("condition", conditionDTO)
                .add("tradePlanDTO", tradePlanDTO)
                .toString();
    }
}
