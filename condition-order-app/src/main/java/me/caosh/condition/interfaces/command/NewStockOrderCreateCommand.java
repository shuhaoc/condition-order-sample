package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/9.
 */
public class NewStockOrderCreateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @DateTimeFormat(pattern = "HH:mm:ss")
    private String purchaseTime;

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(NewStockOrderCreateCommand.class).omitNullValues()
                .addValue(NewStockOrderCreateCommand.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("purchaseTime", purchaseTime)
                .toString();
    }
}
