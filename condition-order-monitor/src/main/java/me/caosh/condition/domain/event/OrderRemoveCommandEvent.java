package me.caosh.condition.domain.event;

import com.google.common.base.MoreObjects;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class OrderRemoveCommandEvent implements OrderCommandEvent {
    private final Long orderId;

    public OrderRemoveCommandEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("orderId", orderId)
                .toString();
    }
}
