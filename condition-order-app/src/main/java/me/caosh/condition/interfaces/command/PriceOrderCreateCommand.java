package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;
import me.caosh.condition.domain.dto.order.DelayConfirmParamDTO;
import me.caosh.condition.domain.dto.order.PriceConditionDTO;
import me.caosh.condition.domain.dto.order.TradePlanDTO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2017/8/9
 */
public class PriceOrderCreateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private SecurityInfoDTO securityInfo;

    @NotNull
    private PriceConditionDTO priceCondition;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

    @NotNull
    private TradePlanDTO tradePlan;

    @Valid
    private DelayConfirmParamDTO delayConfirmParam;

    public SecurityInfoDTO getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfoDTO securityInfo) {
        this.securityInfo = securityInfo;
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

    public DelayConfirmParamDTO getDelayConfirmParam() {
        return delayConfirmParam;
    }

    public void setDelayConfirmParam(DelayConfirmParamDTO delayConfirmParam) {
        this.delayConfirmParam = delayConfirmParam;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(PriceOrderCreateCommand.class).omitNullValues()
                .add("securityInfo", securityInfo)
                .add("priceCondition", priceCondition)
                .add("expireTime", expireTime)
                .add("tradePlan", tradePlan)
                .add("delayConfirmParam", delayConfirmParam)
                .toString();
    }
}
