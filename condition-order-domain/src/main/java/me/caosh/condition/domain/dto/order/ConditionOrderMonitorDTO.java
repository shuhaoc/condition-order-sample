package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.dto.order.constants.OrderCommandType;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/13.
 */
public class ConditionOrderMonitorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private OrderCommandType orderCommandType;
    private Long orderId;
    private ConditionOrderDTO conditionOrderDTO;

    public ConditionOrderMonitorDTO() {
    }

    public ConditionOrderMonitorDTO(OrderCommandType orderCommandType, Long orderId) {
        this.orderCommandType = orderCommandType;
        this.orderId = orderId;
    }

    public ConditionOrderMonitorDTO(OrderCommandType orderCommandType, ConditionOrderDTO conditionOrderDTO) {
        this.orderCommandType = orderCommandType;
        this.orderId = conditionOrderDTO.getOrderId();
        this.conditionOrderDTO = conditionOrderDTO;
    }

    public OrderCommandType getOrderCommandType() {
        return orderCommandType;
    }

    public void setOrderCommandType(OrderCommandType orderCommandType) {
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
        return MoreObjects.toStringHelper(this)
                .add("monitorCommand", orderCommandType)
                .add("conditionOrderDTO", conditionOrderDTO)
                .toString();
    }
}
