package me.caosh.condition.interfaces.command;

import com.google.common.base.MoreObjects;
import hbec.commons.domain.intellitrade.condition.TimeReachedConditionDTO;
import hbec.commons.domain.intellitrade.conditionorder.TradePlanDTO;
import hbec.commons.domain.intellitrade.security.SecurityInfoDTO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by caosh on 2017/8/9.
 */
public class TimeOrderCreateCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private SecurityInfoDTO securityInfo;

    @NotNull
    @Valid
    private TimeReachedConditionDTO condition;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date expireTime;

    @NotNull
    @Valid
    private TradePlanDTO tradePlan;

    public SecurityInfoDTO getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfoDTO securityInfo) {
        this.securityInfo = securityInfo;
    }

    public TimeReachedConditionDTO getCondition() {
        return condition;
    }

    public void setCondition(TimeReachedConditionDTO condition) {
        this.condition = condition;
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
        return MoreObjects.toStringHelper(TimeOrderCreateCommand.class).omitNullValues()
                .add("securityInfo", securityInfo)
                .add("condition", condition)
                .add("expireTime", expireTime)
                .add("tradePlan", tradePlan)
                .toString();
    }
}
