package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.FieldMapping;
import me.caosh.condition.domain.dto.order.PriceConditionDTO;
import me.caosh.condition.domain.dto.order.TradePlanDTO;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by caosh on 2017/8/9.
 */
public class PriceOrderUpdateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long orderId;

    @NotNull
    private PriceConditionDTO priceCondition;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

    @NotNull
    private TradePlanDTO tradePlan;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public PriceConditionDTO getPriceCondition() {
        return priceCondition;
    }

    public void setPriceCondition(PriceConditionDTO priceCondition) {
        this.priceCondition = priceCondition;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public TradePlanDTO getTradePlan() {
        return tradePlan;
    }

    public void setTradePlan(TradePlanDTO tradePlan) {
        this.tradePlan = tradePlan;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceOrderUpdateCommand.class).omitNullValues()
                .add("orderId", orderId)
                .add("priceCondition", priceCondition)
                .add("expireTime", expireTime)
                .add("tradePlan", tradePlan)
                .toString();
    }
}
