package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by caosh on 2017/8/9.
 */
public class NewStockOrderUpdateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long orderId;
    @NotBlank
    @DateTimeFormat(pattern = "HH:mm:ss")
    private String purchaseTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(NewStockOrderUpdateCommand.class).omitNullValues()
                .addValue(NewStockOrderUpdateCommand.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("orderId", orderId)
                .add("purchaseTime", purchaseTime)
                .toString();
    }
}
