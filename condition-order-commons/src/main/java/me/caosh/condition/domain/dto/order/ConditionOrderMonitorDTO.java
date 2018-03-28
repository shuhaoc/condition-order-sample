package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.condorder.ConditionOrderDTO;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/13.
 */
public class ConditionOrderMonitorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer orderCommandType;
    private Long orderId;
    private ConditionOrderDTO conditionOrderDTO;

    public ConditionOrderMonitorDTO() {
    }

    public ConditionOrderMonitorDTO(Integer orderCommandType, Long orderId) {
        this.orderCommandType = orderCommandType;
        this.orderId = orderId;
    }

    public ConditionOrderMonitorDTO(Integer orderCommandType, ConditionOrderDTO conditionOrderDTO) {
        this.orderCommandType = orderCommandType;
        this.orderId = conditionOrderDTO.getOrderId();
        this.conditionOrderDTO = conditionOrderDTO;
    }

    public Integer getOrderCommandType() {
        return orderCommandType;
    }

    public void setOrderCommandType(Integer orderCommandType) {
        this.orderCommandType = orderCommandType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ConditionOrderDTO getConditionOrderDTO() {
        return conditionOrderDTO;
    }

    public void setConditionOrderDTO(ConditionOrderDTO conditionOrderDTO) {
        this.conditionOrderDTO = conditionOrderDTO;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ConditionOrderMonitorDTO.class).omitNullValues()
                .addValue(ConditionOrderMonitorDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("orderCommandType", orderCommandType)
                .add("orderId", orderId)
                .add("conditionOrderDTO", conditionOrderDTO)
                .toString();
    }
}
