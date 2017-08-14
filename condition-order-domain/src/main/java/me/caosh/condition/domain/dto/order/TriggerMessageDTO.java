package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.dto.market.RealTimeMarketDTO;

import java.io.Serializable;

/**
 * Created by caosh on 2017/8/11.
 */
public class TriggerMessageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private TradeSignalDTO tradeSignalDTO;
    private ConditionOrderDTO conditionOrderDTO;
    private RealTimeMarketDTO realTimeMarketDTO;

    public TriggerMessageDTO() {
    }

    public TriggerMessageDTO(TradeSignalDTO tradeSignalDTO, ConditionOrderDTO conditionOrderDTO, RealTimeMarketDTO realTimeMarketDTO) {
        this.tradeSignalDTO = tradeSignalDTO;
        this.conditionOrderDTO = conditionOrderDTO;
        this.realTimeMarketDTO = realTimeMarketDTO;
    }

    public TradeSignalDTO getTradeSignalDTO() {
        return tradeSignalDTO;
    }

    public void setTradeSignalDTO(TradeSignalDTO tradeSignalDTO) {
        this.tradeSignalDTO = tradeSignalDTO;
    }

    public ConditionOrderDTO getConditionOrderDTO() {
        return conditionOrderDTO;
    }

    public void setConditionOrderDTO(ConditionOrderDTO conditionOrderDTO) {
        this.conditionOrderDTO = conditionOrderDTO;
    }

    public RealTimeMarketDTO getRealTimeMarketDTO() {
        return realTimeMarketDTO;
    }

    public void setRealTimeMarketDTO(RealTimeMarketDTO realTimeMarketDTO) {
        this.realTimeMarketDTO = realTimeMarketDTO;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(TriggerMessageDTO.class).omitNullValues()
                .addValue(TriggerMessageDTO.class.getSuperclass() != Object.class ? super.toString() : null)
                .add("tradeSignalDTO", tradeSignalDTO)
                .add("conditionOrderDTO", conditionOrderDTO)
                .add("realTimeMarketDTO", realTimeMarketDTO)
                .toString();
    }
}
