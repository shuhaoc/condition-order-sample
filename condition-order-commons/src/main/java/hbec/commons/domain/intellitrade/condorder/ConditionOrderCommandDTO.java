package hbec.commons.domain.intellitrade.condorder;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/13.
 */
public class ConditionOrderCommandDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer orderCommandType;
    private Long orderId;
    private ConditionOrderDTO conditionOrderDTO;

    public ConditionOrderCommandDTO() {
    }

    public ConditionOrderCommandDTO(Integer orderCommandType, Long orderId) {
        this.orderCommandType = orderCommandType;
        this.orderId = orderId;
    }

    public ConditionOrderCommandDTO(Integer orderCommandType, ConditionOrderDTO conditionOrderDTO) {
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
        return MoreObjects.toStringHelper(ConditionOrderCommandDTO.class).omitNullValues()
                          .addValue(ConditionOrderCommandDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                          .add("orderCommandType", orderCommandType)
                          .add("orderId", orderId)
                          .add("conditionOrderDTO", conditionOrderDTO)
                          .toString();
    }
}
