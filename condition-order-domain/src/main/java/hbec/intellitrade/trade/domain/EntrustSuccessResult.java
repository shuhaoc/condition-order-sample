package hbec.intellitrade.trade.domain;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/8
 */
public class EntrustSuccessResult extends EntrustResult {
    private final Integer entrustCode;
    private final BigDecimal actualEntrustPrice;

    public EntrustSuccessResult(String entrustMessage, Integer entrustCode, BigDecimal actualEntrustPrice) {
        super(SUCCESS, entrustMessage);
        this.entrustCode = entrustCode;
        this.actualEntrustPrice = actualEntrustPrice;
    }

    public Integer getEntrustCode() {
        return entrustCode;
    }

    public BigDecimal getActualEntrustPrice() {
        return actualEntrustPrice;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(EntrustSuccessResult.class).omitNullValues()
                .add("entrustState", getEntrustState())
                .add("entrustMessage", getEntrustMessage())
                .add("entrustCode", entrustCode)
                .add("actualEntrustPrice", actualEntrustPrice)
                .toString();
    }
}
