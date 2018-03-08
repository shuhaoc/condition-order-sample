package me.caosh.condition.domain.dto.trade;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import me.caosh.condition.domain.dto.market.SecurityInfoDTO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
@Convertible
public class EntrustCommandDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SecurityInfoDTO securityInfo = new SecurityInfoDTO();
    private Integer exchangeType;
    private BigDecimal entrustPrice;
    private Integer entrustNumber;
    private Integer orderType;

    public SecurityInfoDTO getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfoDTO securityInfo) {
        this.securityInfo = securityInfo;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(BigDecimal entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public Integer getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(Integer entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustCommandDTO.class).omitNullValues()
                .add("securityInfo", securityInfo)
                .add("exchangeType", exchangeType)
                .add("entrustPrice", entrustPrice)
                .add("entrustNumber", entrustNumber)
                .add("orderType", orderType)
                .toString();
    }
}
