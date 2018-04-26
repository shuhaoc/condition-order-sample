package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by caosh on 2017/8/9.
 */
public class NewStockOrderCreateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @DateTimeFormat(pattern = "HH:mm:ss")
    private String purchaseTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(NewStockOrderCreateCommand.class).omitNullValues()
                .add("purchaseTime", purchaseTime)
                .add("expireTime", expireTime)
                .toString();
    }
}
