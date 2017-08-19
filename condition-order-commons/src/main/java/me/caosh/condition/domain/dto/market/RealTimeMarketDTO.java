package me.caosh.condition.domain.dto.market;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
    private List<BigDecimal> offeredPrices;

    public RealTimeMarketDTO() {
    }

    public RealTimeMarketDTO(Integer type, String code, BigDecimal currentPrice, List<BigDecimal> offeredPrices) {
        this.type = type;
        this.code = code;
        this.currentPrice = currentPrice;
        this.offeredPrices = offeredPrices;
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

    public List<BigDecimal> getOfferedPrices() {
        return offeredPrices;
    }

    public void setOfferedPrices(List<BigDecimal> offeredPrices) {
        this.offeredPrices = offeredPrices;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("code", code)
                .add("currentPrice", currentPrice)
                .add("offeredPrices", offeredPrices)
                .toString();
    }
}
