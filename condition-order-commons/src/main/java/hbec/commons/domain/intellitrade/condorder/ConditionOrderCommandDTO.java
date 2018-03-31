package hbec.commons.domain.intellitrade.condorder;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import hbec.intellitrade.common.market.MarketXID;
import hbec.intellitrade.replay.InputStreamObject;
import hbec.intellitrade.replay.TrackingMarketStreamObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by caosh on 2017/8/13.
 */
public class ConditionOrderCommandDTO implements Serializable, InputStreamObject, TrackingMarketStreamObject {
    private static final long serialVersionUID = 1L;

    private Integer orderCommandType;
    private Long orderId;
    private ConditionOrderDTO conditionOrderDTO;
    private Date opTime;

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

    public ConditionOrderCommandDTO(Integer orderCommandType, Long orderId, Date opTime) {
        this.orderCommandType = orderCommandType;
        this.orderId = orderId;
        this.opTime = opTime;
    }

    public ConditionOrderCommandDTO(Integer orderCommandType,
                                    Long orderId,
                                    ConditionOrderDTO conditionOrderDTO,
                                    Date opTime) {
        this.orderCommandType = orderCommandType;
        this.orderId = orderId;
        this.conditionOrderDTO = conditionOrderDTO;
        this.opTime = opTime;
    }

    @Override
    public Optional<MarketXID> getTrackMarketXID() {
        if (conditionOrderDTO == null) {
            return Optional.absent();
        }
        return conditionOrderDTO.getTrackMarketXID();
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

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    @Override
    public long getInputTimestamp() {
        return opTime.getTime();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ConditionOrderCommandDTO.class).omitNullValues()
                          .add("orderCommandType", orderCommandType)
                          .add("orderId", orderId)
                          .add("conditionOrderDTO", conditionOrderDTO)
                          .add("opTime", opTime)
                          .toString();
    }
}
