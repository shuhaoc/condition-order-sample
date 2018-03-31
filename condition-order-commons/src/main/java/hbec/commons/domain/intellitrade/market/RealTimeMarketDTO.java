package hbec.commons.domain.intellitrade.market;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.replay.InputStreamObject;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.FieldMapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by caosh on 2017/8/14.
 *
 * @author caoshuhao@touker.com
 */
@Convertible
public class RealTimeMarketDTO implements Serializable, InputStreamObject {
    private static final long serialVersionUID = 1L;

    @FieldMapping(mappedProperty = "marketID.type")
    private Integer type;
    @FieldMapping(mappedProperty = "marketID.code")
    private String code;
    private BigDecimal currentPrice;
    private BigDecimal previousPrice;
    private List<BigDecimal> offeredPrices;
    private Date marketTime;

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

    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(BigDecimal previousPrice) {
        this.previousPrice = previousPrice;
    }

    public List<BigDecimal> getOfferedPrices() {
        return offeredPrices;
    }

    public void setOfferedPrices(List<BigDecimal> offeredPrices) {
        this.offeredPrices = offeredPrices;
    }

    public Date getMarketTime() {
        return marketTime;
    }

    public void setMarketTime(Date marketTime) {
        this.marketTime = marketTime;
    }

    @Override
    public long getInputTimestamp() {
        return marketTime.getTime();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(RealTimeMarketDTO.class).omitNullValues()
                .add("type", type)
                .add("code", code)
                .add("currentPrice", currentPrice)
                .add("previousPrice", previousPrice)
                .add("offeredPrices", offeredPrices)
                .add("marketTime", marketTime)
                .toString();
    }
}
