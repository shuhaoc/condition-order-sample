package me.caosh.condition.domain.dto.market;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
public class RealTimeMarketDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer type;
    private String code;
    private BigDecimal currentPrice;

    public RealTimeMarketDTO() {
    }

    public RealTimeMarketDTO(Integer type, String code, BigDecimal currentPrice) {
        this.type = type;
        this.code = code;
        this.currentPrice = currentPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(RealTimeMarketDTO.class).omitNullValues()
                .addValue(RealTimeMarketDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("type", type)
                .add("code", code)
                .add("currentPrice", currentPrice)
                .toString();
    }
}
