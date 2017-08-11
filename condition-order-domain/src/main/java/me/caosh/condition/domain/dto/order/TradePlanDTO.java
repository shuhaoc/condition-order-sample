package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.order.EntrustStrategy;
import me.caosh.condition.domain.model.order.ExchangeType;
import me.caosh.condition.domain.model.order.TradePlan;
import me.caosh.condition.domain.model.share.ValuedEnumUtil;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 *
 * @author caoshuhao@touker.com
 */
public class TradePlanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer exchangeType;
    private Integer entrustStrategy;
    private Integer number;

    public TradePlanDTO() {
    }

    private TradePlanDTO(Integer exchangeType, Integer entrustStrategy, Integer number) {
        this.exchangeType = exchangeType;
        this.entrustStrategy = entrustStrategy;
        this.number = number;
    }

    public static TradePlanDTO fromDomain(TradePlan tradePlan) {
        return new TradePlanDTO(tradePlan.getExchangeType().getValue(),
                tradePlan.getEntrustStrategy().getValue(),
                tradePlan.getNumber());
    }

    public static TradePlan toDomain(TradePlanDTO tradePlanDTO) {
        ExchangeType exchangeType = ValuedEnumUtil.valueOf(tradePlanDTO.getExchangeType(), ExchangeType.class);
        EntrustStrategy entrustStrategy = ValuedEnumUtil.valueOf(tradePlanDTO.getEntrustStrategy(), EntrustStrategy.class);
        return new TradePlan(exchangeType, entrustStrategy, tradePlanDTO.getNumber());
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public Integer getEntrustStrategy() {
        return entrustStrategy;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TradePlanDTO.class).omitNullValues()
                .addValue(TradePlanDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("exchangeType", exchangeType)
                .add("entrustStrategy", entrustStrategy)
                .add("number", number)
                .toString();
    }
}
