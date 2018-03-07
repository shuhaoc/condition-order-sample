package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.Convertible;
import me.caosh.autoasm.FieldMapping;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
@Convertible
public class TradePlanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Range(min = 1, max = 2)
    private Integer exchangeType;
    @NotNull
    @Range(min = 1, max = 11)
    private Integer entrustStrategy;
    @NotNull
    @Range(min = 0, max = 1)
    @FieldMapping(mappedProperty = "tradeNumber.entrustMethod")
    private Integer entrustMethod;
    @NotNull
    @FieldMapping(mappedProperty = "tradeNumber.number")
    private BigDecimal number;

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Integer getEntrustStrategy() {
        return entrustStrategy;
    }

    public void setEntrustStrategy(Integer entrustStrategy) {
        this.entrustStrategy = entrustStrategy;
    }

    public Integer getEntrustMethod() {
        return entrustMethod;
    }

    public void setEntrustMethod(Integer entrustMethod) {
        this.entrustMethod = entrustMethod;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradePlanDTO.class).omitNullValues()
                .add("exchangeType", exchangeType)
                .add("entrustStrategy", entrustStrategy)
                .add("entrustMethod", entrustMethod)
                .add("number", number)
                .toString();
    }
}
