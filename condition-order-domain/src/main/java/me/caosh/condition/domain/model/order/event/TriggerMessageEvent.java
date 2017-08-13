package me.caosh.condition.domain.model.order.event;

import me.caosh.condition.domain.dto.order.ConditionOrderDTO;
import me.caosh.condition.domain.model.signal.TradeSignal;

/**
 * Created by caosh on 2017/8/13.
 */
public class TriggerMessageEvent {
    private final TradeSignal tradeSignal;
    private final ConditionOrderDTO conditionOrderDTO;

    public TriggerMessageEvent(TradeSignal tradeSignal, ConditionOrderDTO conditionOrderDTO) {
        this.tradeSignal = tradeSignal;
        this.conditionOrderDTO = conditionOrderDTO;
    }

    public TradeSignal getTradeSignal() {
        return tradeSignal;
    }

    public ConditionOrderDTO getConditionOrderDTO() {
        return conditionOrderDTO;
    }
}
