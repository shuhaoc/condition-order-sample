package me.caosh.condition.domain.dto.order;

import com.google.common.base.MoreObjects;
import me.caosh.condition.domain.model.market.RealTimeMarket;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/11.
 */
public class TriggerMessageDTO {
    private TradeSignal tradeSignal;
    private ConditionOrderDTO conditionOrderDTO;
    private RealTimeMarket realTimeMarket;

    public TriggerMessageDTO() {
    }

    public TriggerMessageDTO(TradeSignal tradeSignal, ConditionOrderDTO conditionOrderDTO, RealTimeMarket realTimeMarket) {
        this.tradeSignal = tradeSignal;
        this.conditionOrderDTO = conditionOrderDTO;
        this.realTimeMarket = realTimeMarket;
    }

    public TradeSignal getTradeSignal() {
        return tradeSignal;
    }

    public void setTradeSignal(TradeSignal tradeSignal) {
        this.tradeSignal = tradeSignal;
    }

    public ConditionOrderDTO getConditionOrderDTO() {
        return conditionOrderDTO;
    }

    public void setConditionOrderDTO(ConditionOrderDTO conditionOrderDTO) {
        this.conditionOrderDTO = conditionOrderDTO;
    }

    public RealTimeMarket getRealTimeMarket() {
        return realTimeMarket;
    }

    public void setRealTimeMarket(RealTimeMarket realTimeMarket) {
        this.realTimeMarket = realTimeMarket;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tradeSignal", tradeSignal)
                .add("conditionOrderDTO", conditionOrderDTO)
                .add("realTimeMarket", realTimeMarket)
                .toString();
    }
}
